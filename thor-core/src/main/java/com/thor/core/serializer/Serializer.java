package com.thor.core.serializer;

/**
 * @author kay
 * @since v1.0
 */
public interface Serializer {

    <T> byte[] serialize(T object) throws SerializerFairIOException;

    <T> T deserialize(byte[] data, Class<T> clazz) throws SerializerFairIOException;
}
