package com.thor.registry;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.UUID;

@Slf4j
public class ServiceRegistryUtils {

    public static final String CONNECT_STRING = "localhost:2181";
    public static final String ROOT_NODE = "services";
    public static final String APP_NAME = "nodes";

    private static final CuratorFramework curatorFramework;

    // 连接Zookeeper
    static {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(200, 3)).build();
        curatorFramework.start();
    }

    public static CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    /**
     * 定义注册服务的方法
     *
     * @param address
     */
    public static void registryService(InetSocketAddress address) {
        try {
            // 构建注册服务路径
            String serviceUrl = "/" + ROOT_NODE + "/" + APP_NAME;
            if (null == curatorFramework.checkExists().forPath(serviceUrl)) {
                // 创建服务注册根路径
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(serviceUrl);
            }
            // 构建注册服务实例的具体路径
            String addressUrl = serviceUrl + "/" + UUID.randomUUID();
            // 注册服务地址+PORT
            String serviceNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressUrl, JSON.toJSONString(address).getBytes());
            System.out.println("服务注册成功 serviceNode : " + serviceNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
