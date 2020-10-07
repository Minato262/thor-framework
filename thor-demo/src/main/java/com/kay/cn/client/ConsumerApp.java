package com.kay.cn.client;

import com.kay.cn.Order;
import com.kay.cn.server.jar.OrderService;
import com.kay.cn.server.jar.OrderServiceOne;
import com.thoy.transport.request.ThorClient;

public class ConsumerApp {

    public static void main(String[] args) {
        ThorClient client = new ThorClient();

        OrderService orderService = (OrderService) client.create(OrderService.class);
        Order result = orderService.findOrderInfoById(100001L);
        System.out.println("客户端调用结果:" + result.getName() + "," + result.getPrice().doubleValue());

        OrderServiceOne orderServiceOne = (OrderServiceOne) client.create(OrderServiceOne.class);
        result = orderServiceOne.findOrderInfoById(102301L);
        System.out.println("客户端调用结果:" + result.getName() + "," + result.getPrice().doubleValue());
    }
}
