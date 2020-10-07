package com.thoy.transport.invoker;

import com.thor.registry.zk.ZookeeperRegistryCenter;
import com.thor.remoting.Request;
import com.thoy.transport.codec.MessageDecoder;
import com.thoy.transport.codec.MessageEncoder;
import com.thoy.transport.handler.NettyClientTransactionHandler;
import com.thoy.transport.handler.NettyFieldPrependerHandler;
import com.thoy.transport.handler.NettyFrameDecoderHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 *
 * @author kay
 */
@Slf4j
public class MethodProxyInvocation implements InvocationHandler {

    private final Class<?> clazz;

    public MethodProxyInvocation(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 远程连接服务端调用服务端对应方法获取调用结果返回给客户端
        // 1、创建Netty客户的worker线程组
        EventLoopGroup group = new NioEventLoopGroup();

        NettyClientTransactionHandler transactionHandler = new NettyClientTransactionHandler();
        try {
            // 2、创建客户端引导类关联线程组
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .option(ChannelOption.TCP_NODELAY, true)
                     .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            // 获取管道对象
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                            // 解决粘包拆包问题
                            pipeline.addLast(new NettyFrameDecoderHandler());
                            pipeline.addLast(new NettyFieldPrependerHandler());

                            // 编码: 把内容转换为二进制，解码: 二进制转换为对应内容(使用JDK对应 使用序列化)
                            pipeline.addLast(new MessageEncoder());
                            pipeline.addLast(new MessageDecoder());

                            // 添加自定义处理器
                            pipeline.addLast(transactionHandler);
                        }
                     });
            // 通过负载均衡获取对应处理服务实例信息
            ZookeeperRegistryCenter center = new ZookeeperRegistryCenter();
            InetSocketAddress address = center.chooseService();

            // 连接服务端
            ChannelFuture channelFuture = bootstrap.connect(address).sync();

            // 创建客户端传递给服务的数据
            Request request = new Request();
            request.setRequestId(UUID.randomUUID().toString());
            request.setInterfaceName(clazz.getName());
            request.setMethodName(method.getName());
            request.setParameters(method.getParameterTypes());
            request.setArgs(args);

            // 发送给服务端
            channelFuture.channel().writeAndFlush(request).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty error!", e);
        } finally {
            group.shutdownGracefully();
        }

        // 返回调用结果
        return transactionHandler.getResult();
    }
}
