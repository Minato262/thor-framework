package com.thor.proxy;

public interface ProxyFactory {

    <T> T create(Class<T> clazz);
}
