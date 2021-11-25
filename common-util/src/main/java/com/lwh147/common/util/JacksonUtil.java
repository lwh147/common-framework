package com.lwh147.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwh147.common.core.exception.CommonExceptionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 * <p>
 * 按照FastJson的使用逻辑进行封装
 *
 * @author lwh
 * @date 2021/11/19 9:51
 **/
public class JacksonUtil {
    /**
     * 默认使用的ObjectMapper
     * <p>
     * json与java对象属性不全对应时也进行转换
     **/
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 格式化（缩进）的json字符串判断标记
     * <p>
     * 含有回车说明是格式化的json
     **/
    public static final String FORMATTED_JSON_MARK = "\n";

    static {
        // 设置json与java对象属性不全对应时也进行转换
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /*
         * 需要单独使用注解进行配置的项：
         * 空值不转换 @JsonInclude(JsonInclude.Include.NON_NULL)
         * 日期时间格式化 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
         * 序列化为String @JsonSerialize(using = ToStringSerializer.class)
         */
    }

    /**
     * 将格式化的json字符串取消格式化
     *
     * @param json json字符串
     * @return 无格式json字符串
     **/
    public static String unFormatJson(String json) {
        if (json.contains(FORMATTED_JSON_MARK)) {
            // 如果带有格式化字符则取消格式化
            return toJson(parseObject(json, Map.class));
        }
        return json;
    }

    /**
     * 使用默认OM配置将java对象转json的方法
     *
     * @param object 待序列化对象
     * @return json字符串
     **/
    public static String toJson(Object object) {
        return toJson(object, OBJECT_MAPPER);
    }

    /**
     * 使用自定义OM配置将java对象转json的方法
     *
     * @param object       待序列化对象
     * @param objectMapper 自定义OM配置
     * @return json字符串
     **/
    public static String toJson(Object object, ObjectMapper objectMapper) {
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
     * 使用默认OM配置反序列化json的方法
     *
     * @param <T>      目标对象
     * @param json     待反序列化的json
     * @param javaType 目标对象类
     * @return 目标对象
     **/
    public static <T> T parseObject(String json, Class<T> javaType) {
        return parseObject(json, OBJECT_MAPPER, javaType);
    }

    /**
     * 使用自定义OM配置反序列化json的方法
     *
     * @param <T>          目标对象
     * @param json         待反序列化的json
     * @param objectMapper 自定义OM配置
     * @param javaType     目标对象类
     * @return 目标对象
     **/
    public static <T> T parseObject(String json, ObjectMapper objectMapper, Class<T> javaType) {
        T t;
        try {
            t = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("使用Jackson反序列化json[" + json + "]为["
                    + javaType.toString() + "]时发生异常[" + e.getMessage() + "]", e);
        }
        return t;
    }

    /**
     * 使用默认OM配置反序列化json为List的方法
     *
     * @param <T>      目标数组元素对象
     * @param json     待反序列化的json
     * @param itemType 目标数组元素对象类
     * @return 目标对象数组
     **/
    public static <T> List<T> parseList(String json, Class<T> itemType) {
        return parseList(json, OBJECT_MAPPER, itemType);
    }

    /**
     * 使用自定义OM配置反序列化json为List的方法
     *
     * @param <T>          目标数组元素对象
     * @param json         待反序列化的json
     * @param objectMapper 自定义OM配置
     * @param itemType     目标数组元素对象类
     * @return 目标对象数组
     **/
    public static <T> List<T> parseList(String json, ObjectMapper objectMapper, Class<T> itemType) {
        List<T> list;
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, itemType);
        try {
            list = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("使用Jackson反序列化json[" + json + "]为["
                    + javaType.toString() + "]时发生异常[" + e.getMessage() + "]", e);
        }
        return list;
    }
}
