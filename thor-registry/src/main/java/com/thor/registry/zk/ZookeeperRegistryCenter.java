package com.thor.registry.zk;

import com.alibaba.fastjson.JSON;
import com.thor.registry.Registry;
import com.thor.registry.balance.RoundRoBinBalancer;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * zk 注册
 *
 * @author kay
 */
@Slf4j
public class ZookeeperRegistryCenter implements Registry {

    private static final String CONNECT_STRING = "localhost:2181";
    private static final CuratorFramework curatorFramework;

    private static final String APP_NAME = "nodes";
    private static final String ROOT_NODE = "services";

    private List<String> serviceList = new CopyOnWriteArrayList<>();

    static {
        // 连接Zookeeper
        curatorFramework = CuratorFrameworkFactory.builder()
                                                  .connectString(CONNECT_STRING)
                                                  .sessionTimeoutMs(4000)
                                                  .retryPolicy(new ExponentialBackoffRetry(200, 3))
                                                  .build();
        curatorFramework.start();
    }

    public static CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    /**
     * 把启动好的服务实例注册到注册中
     *
     * @param port 端口
     */
    public void connect(int port) {
        try {
            // 获取当前服务器的IP地址
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();

            // 创建网络socket地址对象
            InetSocketAddress address = new InetSocketAddress(ip, port);

            // 调用服务注册方法进行注册
            registry(address);
        } catch (UnknownHostException e) {
            log.error("registry error!", e);
        }
    }

    /**
     * 定义注册服务的方法
     *
     * @param address 网络socket地址对象
     */
    private void registry(InetSocketAddress address) {
        try {
            // 构建注册服务路径
            String serviceUrl = "/" + ROOT_NODE + "/" + APP_NAME;
            if (curatorFramework.checkExists().forPath(serviceUrl) == null) {
                // 创建服务注册根路径
                curatorFramework.create()
                                .creatingParentsIfNeeded()
                                .withMode(CreateMode.PERSISTENT)
                                .forPath(serviceUrl);
            }

            // 构建注册服务实例的具体路径
            String addressUrl = serviceUrl + "/" + UUID.randomUUID();

            // 注册服务地址 + PORT
            String serviceNode = curatorFramework.create()
                                                 .withMode(CreateMode.EPHEMERAL)
                                                 .forPath(addressUrl, JSON.toJSONString(address).getBytes());
            log.info("服务注册成功 serviceNode: {}", serviceNode);
        } catch (Exception e) {
            log.error("registry error!", e);
        }
    }

    /**
     * 根据服务名称筛选所有服务注册的实例列表中负载算出的服务实例对应地址
     */
    public InetSocketAddress chooseService() {
        try {
            // 构建服务注册路径
            String serviceUrl = "/" + ROOT_NODE + "/" + APP_NAME;
            if (curatorFramework.checkExists().forPath(serviceUrl) == null) {
                throw new RuntimeException("调用服务未注册");
            }

            // 获取到了对应服务列表
            serviceList = curatorFramework.getChildren().forPath(serviceUrl);

            // 可能出现服务挂掉情况，动态更新可以用服务列表【注册监听监听节点变】
            dynamicUpdateServiceList(serviceUrl);
            return new RoundRoBinBalancer().roundRobin(serviceList, serviceUrl);
        } catch (Exception e) {
            log.error("choose service error!", e);
        }
        return null;
    }

    /**
     * 动态更新可用服务列表
     */
    private void dynamicUpdateServiceList(String serviceUrl) {
        try {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, serviceUrl, true);

            // 创建一个子节点监听对象
            PathChildrenCacheListener listener = (curatorFramework, pathChildrenCacheEvent) -> {
                serviceList = curatorFramework.getChildren().forPath(serviceUrl);
                log.info("更新服务列表：" + serviceList);
            };
            pathChildrenCache.getListenable().addListener(listener);
            pathChildrenCache.start();
        } catch (Exception e) {
            log.error("choose service error!", e);
        }
    }
}
