package com.thor.proxy;

public interface ProxyFactory {

    <T> Object create(Class<T> clazz);
}
