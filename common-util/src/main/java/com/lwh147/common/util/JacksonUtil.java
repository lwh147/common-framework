package com.lwh147.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lwh147.common.core.exception.CommonExceptionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 * 按照FastJson的使用逻辑进行封装
 *
 * @author lwh
 * @date 2021/11/19 9:51
 **/
public class JacksonUtil {
    /**
     * 默认使用的ObjectMapper配置项：
     * json与java对象属性不全对应时也进行转换
     * java对象为空时也进行转换
     * <p>
     * 注意！需要使用注解进行单独配置的项：
     * <p>
     * 空值不转换 @JsonInclude(JsonInclude.Include.NON_NULL)
     * <p>
     * 日期转换格式 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
     * <p>
     * 可能存在精度丢失问题的数据需要转换为String @JsonSerialize(using = ToStringSerializer.class)
     **/
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    /**
     * 格式化（缩进）的json字符串判断标记
     * <p>
     * 含有回车说明是格式化的json
     **/
    public static final String FORMATTED_JSON_MARK = "\n";

    /**
     * 将格式化的json字符串取消格式化
     *
     * @param json json字符串
     * @return String
     **/
    public static String unFormatJson(String json) {
        // TODO 有待改进
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
     * @return String
     **/
    public static String toJson(Object object) {
        return toJson(object, OBJECT_MAPPER);
    }

    /**
     * 使用自定义OM配置将java对象转json的方法
     *
     * @param object       待序列化对象
     * @param objectMapper 自定义OM配置
     * @return String
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
     * @return T 目标对象
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
     * @return T 目标对象
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
     * @return List<T> 目标数组
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
     * @return List<T> 目标数组
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
