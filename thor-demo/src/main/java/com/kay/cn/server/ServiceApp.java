package com.kay.cn.server;

import com.thoy.transport.request.ThorProvider;

public class ServiceApp {

    public static void main(String[] args) {
        ThorProvider provider = new ThorProvider(11111);
        provider.start();
    }
}
