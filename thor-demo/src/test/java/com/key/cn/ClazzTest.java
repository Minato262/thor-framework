package com.key.cn;

import com.kay.cn.server.jar.OrderService;
import com.kay.cn.server.jar.OrderServiceImpl;
import org.junit.Test;

public class ClazzTest {

    @Test
    public void test() {
        System.out.println(OrderServiceImpl.class.getName());
        System.out.println(OrderService.class.getName());

        System.out.println(OrderServiceImpl.class.getInterfaces()[0].getName());
    }
}
