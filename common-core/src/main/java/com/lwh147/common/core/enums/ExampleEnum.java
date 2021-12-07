package com.lwh147.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lwh147.common.core.exception.CommonExceptionEnum;

/**
 * 枚举类示例
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
     * {@link EnumValue} 指定数据入库时使用该属性作为枚举值，基于 MybatisPlus
     * <p>
     * {@link JsonValue} 指定序列化时使用该属性作为枚举值，基于 Jackson
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
     * {@link JsonCreator} 指定使用此方法进行反序列化，基于Jackson
     *
     * @return 找到的枚举对象，没找到则抛出异常
     * @throws com.lwh147.common.core.exception.CommonException
     **/
    @JsonCreator
    public static ExampleEnum fromValue(String value) {
        for (ExampleEnum e : ExampleEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        // 错误原因可根据业务场景进行定制
        throw CommonExceptionEnum.CLIENT_ARGUMENT_NOT_VALID_ERROR.toException("找不到枚举[ExampleEnum." + value + "]");
    }

    /**
     * 判断枚举值是否存在
     *
     * @return 匹配的枚举对象，不存在返回 {@code null}
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

    @Override
    public String toString() {
        return this.value;
    }
}
