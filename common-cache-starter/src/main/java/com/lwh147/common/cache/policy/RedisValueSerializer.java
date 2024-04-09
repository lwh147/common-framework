package com.lwh147.common.cache.policy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwh147.common.util.constant.DateTimeConstant;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 缓存value序列化策略
 * <p>
 * 默认使用 {@link GenericJackson2JsonRedisSerializer} 作为序列化工具，采用泛型是为了防止反序列化时出现类型转换的相关问题
 * <p>
 * 这样可能会存在缓存数据通用性问题，比如服务A用私有的model作为缓存对象，服务B反序列化时就会有问题，不过实际应该避免出现这种不
 * 同服务使用对方私有model的情况，有待考究
 * <p>
 * 在缓存value未失效的情况下，value对象类型的名称或者包路径发生变化（比如发版后更新了），在获取value后反序列化时也会发生异常
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
     * 供缓存场景使用的ObjectMapper
     **/
    public static final ObjectMapper CACHE_OBJECT_MAPPER = new ObjectMapper();
    /**
     * 基于Jackson序列化为泛型json字符串
     **/
    public static final RedisSerializer<Object> GENERIC_JACKSON_2_JSON_SERIALIZER = new GenericJackson2JsonRedisSerializer(CACHE_OBJECT_MAPPER);

    static {
        // 将BigDecimal转换成PlainString，不采用科学计数法，完整打印数值
        CACHE_OBJECT_MAPPER.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        // JSON与Java对象属性不全对应时也进行反序列化
        CACHE_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 开启泛型支持，仅缓存场景开启
        CACHE_OBJECT_MAPPER.activateDefaultTyping(CACHE_OBJECT_MAPPER.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // 日期时间格式
        CACHE_OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone(DateTimeConstant.DEFAULT_TIMEZONE));
        CACHE_OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimeConstant.DEFAULT_DATETIME_PATTERN));
    }

    private RedisValueSerializer() {
    }

    /**
     * 序列化
     **/
    @Override
    public byte[] serialize(Object o) {
        return GENERIC_JACKSON_2_JSON_SERIALIZER.serialize(o);
    }

    /**
     * 反序列化
     **/
    @Override
    public Object deserialize(byte[] bytes) {
        return GENERIC_JACKSON_2_JSON_SERIALIZER.deserialize(bytes);
    }

}