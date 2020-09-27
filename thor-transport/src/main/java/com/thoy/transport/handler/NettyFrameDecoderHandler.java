package com.thoy.transport.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 自定义长度帧解码器
 *
 * @author kay
 */
public class NettyFrameDecoderHandler extends LengthFieldBasedFrameDecoder {

    public NettyFrameDecoderHandler() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }
}
