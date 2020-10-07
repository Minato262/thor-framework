package com.thoy.transport.handler;

import com.thor.container.Provider;
import com.thor.remoting.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author kay
 */
@Slf4j
public class NettyProviderTransportHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, Object> service;

    public NettyProviderTransportHandler() {
        Provider provider = new Provider();
        service = provider.getService();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result;
        Request data = (Request) msg;
        if (service.containsKey(data.getInterfaceName())) {
            Object instance = service.get(data.getInterfaceName());
            Method declaredMethod = instance.getClass().getDeclaredMethod(data.getMethodName(), data.getParameters());
            result = declaredMethod.invoke(instance, data.getArgs());
        } else {
            throw new ServiceNotFindException();
        }

        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
