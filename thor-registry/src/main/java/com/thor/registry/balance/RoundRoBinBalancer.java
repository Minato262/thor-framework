package com.thor.registry.balance;

import com.alibaba.fastjson.JSON;
import com.thor.registry.zk.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

/**
 * @author kay
 */
@Slf4j
public class RoundRoBinBalancer implements LoadBalance {

    @Override
    public InetSocketAddress roundRobin(List<String> serviceList, String serviceUrl) {
        try {
            // 获取随机负载到地址
            String nodeString = serviceList.get(new Random().nextInt(serviceList.size()));

            // 根据查询路径
            String instanceUrl = serviceUrl + "/" + nodeString;
            String addressString = new String(ZookeeperRegistryCenter.getCuratorFramework().getData().forPath(instanceUrl));

            log.info("负载均衡的服务器为:{}", addressString);
            return JSON.parseObject(addressString, InetSocketAddress.class);
        } catch (Exception e) {
            log.error("");
        }
        return null;
    }
}
