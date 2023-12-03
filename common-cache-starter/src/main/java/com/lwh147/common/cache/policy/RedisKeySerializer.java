package com.lwh147.common.cache.policy;

import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 缓存key序列化策略
 * <p>
 * 默认使用 {@link org.springframework.data.redis.serializer.StringRedisSerializer} 作为序列化工具
 *
 * @author lwh
 * @date 2023/11/18 15:18
 **/
public final class RedisKeySerializer implements RedisSerializer<String> {
    /**
     * 唯一实例
     **/
    public static final RedisKeySerializer INSTANCE = new RedisKeySerializer();

    /**
     * 使用内置的简单String类型序列化工具
     **/
    private final RedisSerializer<String> STRING_SERIALIZER = RedisSerializer.string();

    private RedisKeySerializer() {
    }

    /**
     * 默认序列化策略
     **/
    public byte[] serialize(String o) {
        return STRING_SERIALIZER.serialize(o);
    }

    /**
     * 默认反序列化策略
     **/
    public String deserialize(byte[] bytes) {
        return STRING_SERIALIZER.deserialize(bytes);
    }
}