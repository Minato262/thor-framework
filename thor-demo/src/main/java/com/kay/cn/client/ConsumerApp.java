package com.kay.cn.client;

import com.kay.cn.Order;
import com.kay.cn.server.OrderService;
import com.thoy.transport.netty.NettyClient;

import java.net.InetSocketAddress;

public class ConsumerApp {

    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 11111);

        OrderService orderService = (OrderService) NettyClient.create(OrderService.class, socketAddress);
        Order result = orderService.findOrderInfoById(100001L);
        System.out.println("客户端调用结果:" + result.getName() + "," + result.getPrice().doubleValue());
    }
}
