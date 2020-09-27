package com.thoy.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClientTransactionHandler extends ChannelInboundHandlerAdapter {

    private Object result;

    public Object getResult() {
        return result;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("RPC invoker fail");
    }
}
