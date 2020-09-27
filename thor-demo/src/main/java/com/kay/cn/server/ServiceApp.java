package com.kay.cn.server;

import com.thoy.transport.netty.NettyProvider;

public class ServiceApp {

    public static void main(String[] args) {
        NettyProvider rpcServer = new NettyProvider(11111);
        rpcServer.start();
    }
}
