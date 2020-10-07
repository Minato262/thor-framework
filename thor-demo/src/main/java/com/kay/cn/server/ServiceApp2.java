package com.kay.cn.server;

import com.thoy.transport.request.ThorProvider;

public class ServiceApp2 {

    public static void main(String[] args) {
        ThorProvider provider = new ThorProvider(11113);
        provider.start();
    }
}
