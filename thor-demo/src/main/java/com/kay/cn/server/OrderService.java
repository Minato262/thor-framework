package com.kay.cn.server;

import com.kay.cn.Order;

public interface OrderService {

    Order findOrderInfoById(Long id);
}
