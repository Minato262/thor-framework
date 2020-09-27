package com.thor.core.serializer.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thor.core.serializer.Serializer;
import com.thor.core.serializer.SerializerFairIOException;

import java.io.IOException;

/**
 * @author kay
 * @since v1.0
 */
public class JsonSerializer implements Serializer {

    private ObjectMapper objectMapper;

    @Override
    public <T> byte[] serialize(T object) throws SerializerFairIOException {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new SerializerFairIOException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws SerializerFairIOException {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new SerializerFairIOException(e);
        }
    }
}
