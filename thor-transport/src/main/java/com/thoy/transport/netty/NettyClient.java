package com.thoy.transport.netty;

import com.thor.remoting.RequestData;
import com.thoy.transport.handler.NettyClientTransactionHandler;
import com.thoy.transport.handler.NettyFieldPrependerHandler;
import com.thoy.transport.handler.NettyFrameDecoderHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

@Slf4j
public class NettyClient {

    public static <T> Object create(Class<T> interfaceClass, InetSocketAddress address) {
        // 创建一个方法代理对象
        MethodProxy methodProxy = new MethodProxy(interfaceClass, address);
        Class<?>[] interfaces = interfaceClass.isInterface() ? new Class<?>[]{interfaceClass} : interfaceClass.getInterfaces();
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), interfaces, methodProxy);
    }

    private static class MethodProxy implements InvocationHandler {

        private final Class<?> clazz;
        private final InetSocketAddress address;

        public MethodProxy(Class<?> clazz, InetSocketAddress address) {
            this.clazz = clazz;
            this.address = address;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 远程连接服务端调用服务端对应方法获取调用结果返回给客户端
            // 1、创建Netty客户的worker线程组
            EventLoopGroup group = new NioEventLoopGroup();

            NettyClientTransactionHandler rpcClientHandler = new NettyClientTransactionHandler();
            try {
                // 2、创建客户端引导类关联线程组
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group).channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) {
                                // 获取管道对象
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new LoggingHandler());

                                // 解决粘包拆包问题
                                pipeline.addLast(new NettyFrameDecoderHandler());
                                pipeline.addLast(new NettyFieldPrependerHandler());

                                // 编码: 把内容转换为二进制，解码: 二进制转换为对应内容(使用JDK对应 使用序列化)
                                pipeline.addLast("encoder", new ObjectEncoder());
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                                // 添加自定义处理器
                                pipeline.addLast(rpcClientHandler);
                            }
                        });
                // 连接服务端
                ChannelFuture channelFuture = bootstrap.connect(address).sync();
                // 创建客户端传递给服务的数据
                RequestData requestData = new RequestData();
                requestData.setInterfaceName(clazz.getName());
                requestData.setMethodName(method.getName());
                requestData.setParameterTypes(method.getParameterTypes());
                requestData.setArgs(args);
                // 发送给服务端
                channelFuture.channel().writeAndFlush(requestData).sync();
                // 关闭资源
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }

            // 返回调用结果
            return rpcClientHandler.getResult();
        }
    }
}
