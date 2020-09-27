package com.thoy.transport.netty;

import com.thor.registry.ServiceRegistryUtils;
import com.thoy.transport.handler.NettyFieldPrependerHandler;
import com.thoy.transport.handler.NettyFrameDecoderHandler;
import com.thoy.transport.handler.NettyIdleStateHandler;
import com.thoy.transport.handler.NettyProviderTransportHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Slf4j
public class NettyProvider {

    private final int port;

    public NettyProvider(int port) {
        this.port = port;
    }

    public void start() {
        // 创建2个对象
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 创建一个服务端引导对象组装线程组
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
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
                            pipeline.addLast(new NettyIdleStateHandler());
                            pipeline.addLast(new NettyProviderTransportHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("Netty RPC Starting Listen " + this.port);

            // 启动成功之后注册到注册中心
            serviceRegistry(port);

            // 释放连接
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty error!", e);
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    /**
     * 把启动好的服务实例注册到注册中
     *
     * @param port 端口
     */
    private void serviceRegistry(int port) {
        try {
            // 获取当前服务器的IP地址
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            // 创建网络socket地址对象
            InetSocketAddress address = new InetSocketAddress(ip, port);
            // 调用服务注册方法进行注册
            ServiceRegistryUtils.registryService(address);
        } catch (UnknownHostException e) {
            log.error("registry error!", e);
        }
    }
}
