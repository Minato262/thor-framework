package com.thoy.transport.handler;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 *
 */
public class NettyFieldPrependerHandler extends LengthFieldPrepender {

    public NettyFieldPrependerHandler() {
        super(4);
    }
}
