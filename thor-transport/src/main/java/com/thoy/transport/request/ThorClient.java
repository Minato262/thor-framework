package com.thoy.transport.request;

import com.thor.proxy.AbstractProxyFactory;
import com.thoy.transport.invoker.MethodProxyInvocation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *
 * @author kay
 */
@Slf4j
public class ThorClient extends AbstractProxyFactory {

    public ThorClient(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public <T> Object create(Class<T> interfaceClass) {
        InvocationHandler methodProxy = new MethodProxyInvocation(interfaceClass, this.getAddress());
        Class<?>[] interfaces = getClazz(interfaceClass);
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), interfaces, methodProxy);
    }

    private <T> Class<?>[] getClazz(Class<T> interfaceClass) {
        if (interfaceClass.isInterface()) {
            return new Class<?>[]{interfaceClass};
        } else {
            return interfaceClass.getInterfaces();
        }
    }
}
