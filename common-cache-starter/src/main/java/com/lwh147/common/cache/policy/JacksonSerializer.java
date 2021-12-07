package com.lwh147.common.cache.policy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * Jackson序列化器
 * <p>
 * 封装了 {@link GenericJackson2JsonRedisSerializer}
 *
 * @author lwh
 * @date 2021/12/7 11:01
 **/
public class JacksonSerializer {
    private static final ObjectMapper OM;
    private static final GenericJackson2JsonRedisSerializer SERIALIZER;

    static {
        OM = new ObjectMapper();
        // json与java对象属性不全对应时也进行反序列化
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SERIALIZER = new GenericJackson2JsonRedisSerializer(OM);
    }

    public static byte[] serialize(Object o) {
        return SERIALIZER.serialize(o);
    }

    public static Object deserialize(byte[] bytes) {
        return SERIALIZER.deserialize(bytes);
    }

    private JacksonSerializer() {
    }
}
