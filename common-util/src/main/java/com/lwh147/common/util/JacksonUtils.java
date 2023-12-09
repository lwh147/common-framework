package com.lwh147.common.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lwh147.common.model.constant.DateTimeConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * JSON工具类，基于Jackson封装实现
 *
 * @author lwh
 * @date 2021/11/19 9:51
 **/
public final class JacksonUtils {
    /**
     * 默认使用的ObjectMapper
     **/
    public static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();
    /**
     * 供缓存场景使用的ObjectMapper
     **/
    public static final ObjectMapper CACHE_OBJECT_MAPPER = new ObjectMapper();

    private JacksonUtils() {}

    static {
        /*
         * 常用注解：
         * 空值不转换 @JsonInclude(JsonInclude.Include.NON_NULL)
         * 日期时间格式化 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
         * 序列化为String或配置自定义序列化策略 @JsonSerialize(using = ToStringSerializer.class)
         */

        SimpleModule simpleModule = new SimpleModule();

        // 将Long转换成String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);

        DEFAULT_OBJECT_MAPPER.registerModule(simpleModule);
        CACHE_OBJECT_MAPPER.registerModule(simpleModule);

        // 将BigDecimal转换成PlainString，不采用科学计数法，完整打印数值
        DEFAULT_OBJECT_MAPPER.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        CACHE_OBJECT_MAPPER.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

        // 日期时间格式
        DEFAULT_OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone(DateTimeConstant.DEFAULT_TIMEZONE));
        CACHE_OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone(DateTimeConstant.DEFAULT_TIMEZONE));
        DEFAULT_OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimeConstant.DEFAULT_DATETIME_PATTERN));
        CACHE_OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimeConstant.DEFAULT_DATETIME_PATTERN));

        // JSON与Java对象属性不全对应时也进行反序列化，缓存场景默认不开启
        DEFAULT_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 开启泛型支持，仅缓存场景开启
        CACHE_OBJECT_MAPPER.activateDefaultTyping(DEFAULT_OBJECT_MAPPER.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    /**
     * 使用默认OM配置将Java对象转JSON字符串的方法
     *
     * @param object 待序列化对象
     * @return JSON字符串
     **/
    public static String toJsonStr(Object object) {
        return toJsonStr(object, DEFAULT_OBJECT_MAPPER);
    }

    /**
     * 使用自定义OM配置将Java对象转JSON字符串的方法
     *
     * @param object       待序列化对象
     * @param objectMapper 自定义OM配置
     * @return JSON字符串
     **/
    public static String toJsonStr(Object object, ObjectMapper objectMapper) {
        String res;
        try {
            res = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("使用Jackson序列化对象时发生异常：" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 使用默认OM配置反序列化JSON字符串的方法
     *
     * @param <T>      目标对象
     * @param jsonStr  待反序列化的JSON字符串
     * @param javaType 目标对象类
     * @return 目标对象
     **/
    public static <T> T parseObject(String jsonStr, Class<T> javaType) {
        return parseObject(jsonStr, DEFAULT_OBJECT_MAPPER, javaType);
    }

    /**
     * 使用自定义OM配置反序列化JSON字符串的方法
     *
     * @param <T>          目标对象
     * @param jsonStr      待反序列化的JSON字符串
     * @param objectMapper 自定义OM配置
     * @param javaType     目标对象类
     * @return 目标对象
     **/
    public static <T> T parseObject(String jsonStr, ObjectMapper objectMapper, Class<T> javaType) {
        T t;
        try {
            t = objectMapper.readValue(jsonStr, javaType);
        } catch (Exception e) {
            throw new RuntimeException("使用Jackson反序列化json字符串时发生异常：" + e.getMessage(), e);
        }
        return t;
    }

    /**
     * 使用默认OM配置反序列化JSON字符串为 {@link List} 的方法
     *
     * @param <T>      目标数组元素对象
     * @param jsonStr  待反序列化的JSON字符串
     * @param itemType 目标数组元素对象类
     * @return 目标对象数组
     **/
    public static <T> List<T> parseList(String jsonStr, Class<T> itemType) {
        return parseList(jsonStr, DEFAULT_OBJECT_MAPPER, itemType);
    }

    /**
     * 使用自定义OM配置反序列化JSON字符串为 {@link List} 的方法
     *
     * @param <T>          目标数组元素对象
     * @param jsonStr      待反序列化的JSON字符串
     * @param objectMapper 自定义OM配置
     * @param itemType     目标数组元素对象类
     * @return 目标对象数组
     **/
    public static <T> List<T> parseList(String jsonStr, ObjectMapper objectMapper, Class<T> itemType) {
        List<T> list;
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, itemType);
        try {
            list = objectMapper.readValue(jsonStr, javaType);
        } catch (Exception e) {
            throw new RuntimeException("使用Jackson反序列化json字符串时发生异常：" + e.getMessage(), e);
        }
        return list;
    }
}