package com.thor.registry.zk;

import com.thor.registry.Registry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * zk 注册
 *
 * @author kay
 */
@Slf4j
public class ZookeeperRegistryCenter implements Registry {

    private static final String CONNECT_STRING = "localhost:2181";
    private static final String ROOT_NODE = "services";
    private static final String APP_NAME = "nodes";

    private static final CuratorFramework curatorFramework;

    static {
        // 连接Zookeeper
        curatorFramework = CuratorFrameworkFactory.builder()
                                                  .connectString(CONNECT_STRING)
                                                  .sessionTimeoutMs(4000)
                                                  .retryPolicy(new ExponentialBackoffRetry(200, 3))
                                                  .build();
        curatorFramework.start();
    }

    /**
     * 把启动好的服务实例注册到注册中
     *
     * @param port 端口
     */
    public ZookeeperRegistryCenter(int port) {
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
                                                 .forPath(addressUrl, address.getHostName().getBytes());
            log.info("服务注册成功 serviceNode: {}", serviceNode);
        } catch (Exception e) {
            log.error("registry error!", e);
        }
    }
}
