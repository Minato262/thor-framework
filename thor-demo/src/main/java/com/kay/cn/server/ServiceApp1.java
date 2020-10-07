package com.kay.cn.server;

import com.thoy.transport.request.ThorProvider;

public class ServiceApp1 {

    public static void main(String[] args) {
        ThorProvider provider = new ThorProvider(11112);
        provider.start();
    }
}
