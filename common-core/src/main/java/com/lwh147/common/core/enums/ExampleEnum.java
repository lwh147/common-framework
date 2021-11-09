package com.lwh147.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lwh147.common.core.exception.CommonExceptionEnum;

/**
 * 枚举类示例
 * <p>
 * 必须实现接口 {@code ICommonEnum}
 * <p>
 * 必备方法 {@code fromValue()} 和 {@code exist()}
 * <p>
 * 必备注解 {@code @EnumValue} 和 {@code @JsonValue} 及 {@code @JsonCreator}
 *
 * @author lwh
 * @date 2021/10/26 16:45
 **/
public enum ExampleEnum implements ICommonEnum {
    /**
     * 示例
     **/
    EXAMPLE("EXAMPLE", "示例"),
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
    ExampleEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据枚举值寻找枚举对象
     * <p>
     * {@code @JsonCreator} jackson注解，将使用此方法进行反序列化
     *
     * @return ExampleEnum 找到的枚举对象
     * @throws com.lwh147.common.core.exception.CommonException
     **/
    @JsonCreator
    public static ExampleEnum fromValue(String value) {
        for (ExampleEnum e : ExampleEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw CommonExceptionEnum.OTHER.toException("找不到枚举【ExampleEnum." + value + "】");
    }

    /**
     * 判断枚举值是否存在
     *
     * @return ExampleEnum 匹配的枚举对象，不存在返回null
     **/
    public static ExampleEnum exist(String value) {
        for (ExampleEnum e : ExampleEnum.values()) {
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
