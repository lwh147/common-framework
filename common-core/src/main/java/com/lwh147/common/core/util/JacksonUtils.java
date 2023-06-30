package com.lwh147.common.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwh147.common.core.exception.CommonExceptionEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Jackson工具类
 * <p>
 * 按照FastJson的使用逻辑进行封装
 *
 * @author lwh
 * @date 2021/11/19 9:51
 **/
public final class JacksonUtils {
    /**
     * 默认使用的ObjectMapper
     **/
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JacksonUtils() {}

    /*
     * 常用注解：
     * 空值不转换 @JsonInclude(JsonInclude.Include.NON_NULL)
     * 日期时间格式化 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
     * 序列化为String或配置自定义序列化策略 @JsonSerialize(using = ToStringSerializer.class)
     */
    static {
        // 将BigDecimal转换成PlainString，不采用科学计数法，完整打印数值
        OBJECT_MAPPER.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        // JSON与Java对象属性不全对应时也进行反序列化
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 使用默认OM配置将Java对象转JSON字符串的方法
     *
     * @param object 待序列化对象
     * @return JSON字符串
     **/
    public static String toJsonStr(Object object) {
        return toJsonStr(object, OBJECT_MAPPER);
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
            throw CommonExceptionEnum.COMMON_ERROR.toException("使用Jackson序列化对象[" + object.getClass().toString()
                    + "]时发生异常[" + e.getMessage() + "]", e);
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
        return parseObject(jsonStr, OBJECT_MAPPER, javaType);
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
            throw CommonExceptionEnum.COMMON_ERROR.toException("使用Jackson反序列化json[" + jsonStr + "]为["
                    + javaType.toString() + "]时发生异常[" + e.getMessage() + "]", e);
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
        return parseList(jsonStr, OBJECT_MAPPER, itemType);
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
            throw CommonExceptionEnum.COMMON_ERROR.toException("使用Jackson反序列化json[" + jsonStr + "]为["
                    + javaType.toString() + "]时发生异常[" + e.getMessage() + "]", e);
        }
        return list;
    }
}