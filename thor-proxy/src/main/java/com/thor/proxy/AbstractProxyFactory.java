package com.thor.proxy;

import java.net.InetSocketAddress;

public abstract class AbstractProxyFactory implements ProxyFactory {

    private final InetSocketAddress address;

    public AbstractProxyFactory(String hostname, int port) {
        this.address = new InetSocketAddress(hostname, port);
    }

    protected InetSocketAddress getAddress() {
        return this.address;
    }
}
