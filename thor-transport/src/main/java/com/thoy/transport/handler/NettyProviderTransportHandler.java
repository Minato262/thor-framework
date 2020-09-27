package com.thoy.transport.handler;

import com.thor.container.annotation.ThorProvider;
import com.thor.container.resoure.ClassResourceLoader;
import com.thor.container.resoure.Resource;
import com.thor.remoting.RequestData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author kay
 */
@Slf4j
public class NettyProviderTransportHandler extends ChannelInboundHandlerAdapter {
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final List<Class<?>> classCaches = new CopyOnWriteArrayList<>();

    public NettyProviderTransportHandler() {
        doScan();
        doRegisterService();
    }

    /**
     * 扫描程序编译目录下面所有的.class
     */
    private void doScan() {
        try {
            Resource resource = new ClassResourceLoader();
            classCaches.addAll(resource.getClasses());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过注解解析完成暴露服务的注册
     */
    private void doRegisterService() {
        if (!classCaches.isEmpty()) {
            try {
                for (Class<?> cls : classCaches) {
                    // 判断是否标注了@RPCServer注解
                    if (cls.isAnnotationPresent(ThorProvider.class)) {
                        // 获取RPCServer对象
                        // 获取注解配置的暴露服务对应接口
                        // 添加到暴露服务列表中
                        Class<?> interfaceClass = cls.getInterfaces()[0];
                        serviceMap.put(interfaceClass.getName(), cls.newInstance());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result;
        RequestData data = (RequestData) msg;
        if (serviceMap.containsKey(data.getInterfaceName())) {
            Object instance = serviceMap.get(data.getInterfaceName());
            Method declaredMethod = instance.getClass().getDeclaredMethod(data.getMethodName(), data.getParameterTypes());
            result = declaredMethod.invoke(instance, data.getArgs());
        } else {
            throw new RuntimeException("调用服务不存在！");
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
