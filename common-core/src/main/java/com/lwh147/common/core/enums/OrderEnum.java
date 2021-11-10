package com.lwh147.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lwh147.common.core.exception.CommonExceptionEnum;

/**
 * 排序规则枚举
 *
 * @author lwh
 * @date 2021/11/9 10:30
 **/
public enum OrderEnum implements ICommonEnum {
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
     * {@code @EnumValue} mybatis-plus注解，该注解所注属性将被作为value存入数据库
     * {@code @JsonValue} jackson注解，枚举对象序列化时该注解所注属性将被作为value
     **/
    @EnumValue
    @JsonValue
    private final String value;
    /**
     * 枚举值描述
     **/
    private final String name;

    /**
     * 构造方法，默认私有
     **/
    OrderEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据枚举值寻找枚举对象
     * <p>
     * {@code @JsonCreator} jackson注解，将使用此方法进行反序列化
     *
     * @return OrderEnum 找到的枚举对象
     * @throws com.lwh147.common.core.exception.CommonException
     **/
    @JsonCreator
    public static OrderEnum fromValue(String value) {
        for (OrderEnum e : OrderEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw CommonExceptionEnum.CLIENT_ARGUMENT_NOT_VALID_ERROR.toException("没有排序规则【" + value + "】");
    }

    /**
     * 判断枚举值是否存在
     *
     * @return OrderEnum 匹配的枚举对象，不存在返回null
     **/
    public static OrderEnum exist(String value) {
        for (OrderEnum e : OrderEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
