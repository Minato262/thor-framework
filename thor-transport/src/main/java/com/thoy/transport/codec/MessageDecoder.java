package com.thoy.transport.codec;

import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author kay
 */
@Slf4j
public class MessageDecoder extends ObjectDecoder {

    public MessageDecoder() {
        super(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null));
    }
}
