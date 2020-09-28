package com.thoy.transport.codec;

import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

public class MessageDecoder extends ObjectDecoder {

    public MessageDecoder() {
        super(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null));
    }
}
