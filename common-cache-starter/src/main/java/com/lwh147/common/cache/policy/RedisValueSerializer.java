package com.lwh147.common.cache.policy;

import com.lwh147.common.util.JacksonUtils;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 缓存value序列化策略
 * <p>
 * 默认使用 {@link GenericJackson2JsonRedisSerializer} 作为序列化工具，采用泛型是为了防止反序列化时出现类型转换的相关问题
 * <p>
 * 这样可能会存在缓存数据通用性问题，比如服务A用私有的model作为缓存对象，服务B反序列化时就会有问题，不过实际应该避免出现这种不
 * 同服务使用对方私有model的情况，有待考究
 *
 * @author lwh
 * @date 2021/12/7 11:01
 **/
public final class RedisValueSerializer implements RedisSerializer<Object> {
    /**
     * 默认实例
     **/
    public static final RedisValueSerializer INSTANCE = new RedisValueSerializer();

    /**
     * 基于Jackson序列化为泛型json字符串
     **/
    public static final RedisSerializer<Object> GENERIC_JACKSON_2_JSON_SERIALIZER = new GenericJackson2JsonRedisSerializer(JacksonUtils.CACHE_OBJECT_MAPPER);

    private RedisValueSerializer() {
    }

    /**
     * 序列化
     **/
    public byte[] serialize(Object o) {
        return GENERIC_JACKSON_2_JSON_SERIALIZER.serialize(o);
    }

    /**
     * 反序列化
     **/
    public Object deserialize(byte[] bytes) {
        return GENERIC_JACKSON_2_JSON_SERIALIZER.deserialize(bytes);
    }

}