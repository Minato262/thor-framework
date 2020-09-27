package com.thoy.transport.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

/**
 * 这里是出去的数据信息
 *
 * @author kay
 */
@Slf4j
public class NettyConnectManageHandler extends ChannelDuplexHandler {

    @Override
    public void connect(ChannelHandlerContext ctx,
                        SocketAddress remoteAddress,
                        SocketAddress localAddress,
                        ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
        if (log.isDebugEnabled()) {
            log.debug("CONNECT SERVER [{}]", remoteAddress.toString());
        }
    }
}
