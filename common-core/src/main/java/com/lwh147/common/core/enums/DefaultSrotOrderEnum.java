package com.lwh147.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lwh147.common.core.exception.CommonExceptionEnum;

/**
 * 排序规则枚举
 *
 * @author lwh
 * @date 2021/11/9 10:30
 **/
public enum DefaultSrotOrderEnum {
    /**
     * 升序
     **/
    ASC("ASC", "升序"),
    /**
     * 降序
     **/
    DESC("DESC", "降序"),
    ;

    /**
     * 枚举值
     * <p>
     * {@link JsonValue} 指定序列化时使用该属性作为枚举值，基于 Jackson
     **/
    @JsonValue
    private final String value;
    /**
     * 枚举值描述
     **/
    private final String name;

    /**
     * 构造方法，默认私有
     **/
    DefaultSrotOrderEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据枚举值寻找枚举对象
     * <p>
     * {@link JsonCreator} 指定使用此方法进行反序列化，基于Jackson
     *
     * @return 找到的枚举对象
     * @throws com.lwh147.common.core.exception.CommonException
     **/
    @JsonCreator
    public static DefaultSrotOrderEnum fromValue(String value) {
        for (DefaultSrotOrderEnum e : DefaultSrotOrderEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw CommonExceptionEnum.CLIENT_ARGUMENT_NOT_VALID_ERROR.toException("没有排序规则[" + value + "]");
    }

    /**
     * 判断枚举值是否存在
     *
     * @return 匹配的枚举对象，不存在返回null
     **/
    public static DefaultSrotOrderEnum exist(String value) {
        for (DefaultSrotOrderEnum e : DefaultSrotOrderEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.value;
    }
}