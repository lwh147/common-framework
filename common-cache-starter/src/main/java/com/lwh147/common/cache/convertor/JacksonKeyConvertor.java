package com.lwh147.common.cache.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwh147.common.core.exception.CommonExceptionEnum;

import java.util.function.Function;

/**
 * 使用Jackson将缓存key对象转换为String
 *
 * @author lwh
 * @date 2021/11/29 14:31
 **/
public class JacksonKeyConvertor implements Function<Object, Object> {
    public static final JacksonKeyConvertor INSTANCE = new JacksonKeyConvertor();

    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            return null;
        }
        if (originalKey instanceof String) {
            return originalKey;
        }
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(originalKey);
        } catch (JsonProcessingException e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("序列化key时发生异常[" + e.getMessage() + "]", e);
        }
    }
}
