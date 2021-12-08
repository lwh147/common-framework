package com.lwh147.common.cache.policy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
    public static final StringRedisSerializer STRING_SERIALIZER;
    public static final GenericJackson2JsonRedisSerializer JACKSON_SERIALIZER;

    static {
        OM = new ObjectMapper();
        // json与java对象属性不全对应时也进行反序列化
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JACKSON_SERIALIZER = new GenericJackson2JsonRedisSerializer(OM);
        STRING_SERIALIZER = new StringRedisSerializer();
    }

    public static byte[] serialize(Object o) {
        return JACKSON_SERIALIZER.serialize(o);
    }

    public static Object deserialize(byte[] bytes) {
        return JACKSON_SERIALIZER.deserialize(bytes);
    }

    private JacksonSerializer() {
    }
}
