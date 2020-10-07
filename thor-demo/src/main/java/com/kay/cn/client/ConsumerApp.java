package com.kay.cn.client;

import com.kay.cn.Order;
import com.kay.cn.server.OrderService;
import com.kay.cn.server.OrderServiceOne;
import com.thoy.transport.request.ThorClient;

public class ConsumerApp {

    public static void main(String[] args) {
        ThorClient client = new ThorClient("127.0.0.1", 11111);

        OrderService orderService = (OrderService) client.create(OrderService.class);
        Order result = orderService.findOrderInfoById(100001L);
        System.out.println("客户端调用结果:" + result.getName() + "," + result.getPrice().doubleValue());

        OrderServiceOne orderServiceOne = (OrderServiceOne) client.create(OrderServiceOne.class);
        result = orderServiceOne.findOrderInfoById(100001L);
        System.out.println("客户端调用结果:" + result.getName() + "," + result.getPrice().doubleValue());
    }
}
