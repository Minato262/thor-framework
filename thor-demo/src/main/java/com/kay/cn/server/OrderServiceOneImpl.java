package com.kay.cn.server;

import com.kay.cn.Order;
import com.thor.container.annotation.ThorProvider;

import java.math.BigDecimal;

@ThorProvider
public class OrderServiceOneImpl implements OrderServiceOne {

    @Override
    public Order findOrderInfoById(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setName("测试订单1:");
        order.setPrice(new BigDecimal("2323.4"));
        return order;
    }
}
