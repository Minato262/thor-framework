package com.thoy.transport.handler;

import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳检测处理器
 *
 * @author kay
 */
@Slf4j
public class NettyIdleStateHandler extends IdleStateHandler {

    public NettyIdleStateHandler() {
        super(20, 40, 60);
    }
}
