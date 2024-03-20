package com.lwh147.common.cache.policy;

import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 缓存key序列化策略
 * <p>
 * 基于 {@link org.springframework.data.redis.serializer.StringRedisSerializer} 作为序列化工具
 *
 * @author lwh
 * @apiNote 需要注意的是：如果key是非String类型，默认会使用 {@link CacheKeyConverter#convert(Object)} 将key转化为String类型后序列化，
 * 这里没有考虑需要将key反序列化回原对象的情况，缓存key一般也不会有反序列化的需求
 * @date 2023/11/18 15:18
 **/
public final class RedisKeySerializer implements RedisSerializer<Object> {
    /**
     * 默认实例
     **/
    public static final RedisKeySerializer INSTANCE = new RedisKeySerializer();

    /**
     * 使用内置的简单String类型序列化工具
     **/
    public static final RedisSerializer<String> STRING_SERIALIZER = RedisSerializer.string();

    private RedisKeySerializer() {
    }

    /**
     * 序列化
     **/
    public byte[] serialize(Object o) {
        return STRING_SERIALIZER.serialize(CacheKeyConverter.convert(o));
    }

    /**
     * 反序列化
     **/
    public Object deserialize(byte[] bytes) {
        return STRING_SERIALIZER.deserialize(bytes);
    }
}