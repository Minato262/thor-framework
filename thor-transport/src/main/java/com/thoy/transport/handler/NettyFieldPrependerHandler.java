package com.thoy.transport.handler;

import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author kay
 */
@Slf4j
public class NettyFieldPrependerHandler extends LengthFieldPrepender {

    public NettyFieldPrependerHandler() {
        super(4);
    }
}
