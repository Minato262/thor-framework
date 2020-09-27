package com.thoy.transport.handler;

import io.netty.handler.timeout.IdleStateHandler;

/**
 * 心跳检测处理器
 *
 * @author kay
 */
public class NettyIdleStateHandler extends IdleStateHandler {

    public NettyIdleStateHandler() {
        super(20, 40, 60);
    }
}
