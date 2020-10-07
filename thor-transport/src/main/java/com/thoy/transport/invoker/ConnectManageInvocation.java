package com.thoy.transport.invoker;

import com.thor.registry.zk.ZookeeperRegistryCenter;
import com.thoy.transport.codec.MessageDecoder;
import com.thoy.transport.codec.MessageEncoder;
import com.thoy.transport.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author kay
 */
@Slf4j
public class ConnectManageInvocation implements ConnectManage {

    @Override
    public void connect(int port) {
        // 创建2个对象
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 创建一个服务端引导对象组装线程组
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGroup)
                     .channel(NioServerSocketChannel.class)
                     .option(ChannelOption.SO_BACKLOG, 128)
                     .childOption(ChannelOption.SO_KEEPALIVE, true)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
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
                            pipeline.addLast(new NettyIdleStateHandler());
                            pipeline.addLast(new NettyProviderTransportHandler());
                            pipeline.addLast(new NettyConnectManageHandler());
                        }
                    });
            // 绑定端口
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("Netty RPC Starting Listen: {}", port);

            // 启动成功之后注册到注册中心
            new ZookeeperRegistryCenter(port);

            // 释放连接
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty error!", e);
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
