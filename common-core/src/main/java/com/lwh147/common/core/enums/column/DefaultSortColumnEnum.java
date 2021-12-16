package com.lwh147.common.core.enums.column;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lwh147.common.core.exception.CommonExceptionEnum;

/**
 * 默认排序字段枚举类，只有创建时间
 * <p>
 * {@code value} 代表前端传参取值
 * <p>
 * {@code name} 代表参数实际的数据库字段名
 * <p>
 * 这样能够避免暴露数据库字段名称到前端，增加安全性
 *
 * @author lwh
 * @date 2021/12/12 23:17
 **/
public enum DefaultSortColumnEnum implements IColumnEnum {
    /**
     * 默认排序字段，创建时间
     **/
    CREATE_TIME("createTime", "create_time"),
    ;

    /**
     * 枚举值，给前端使用的字段名
     * <p>
     * {@link JsonValue} 指定序列化时使用该属性作为枚举值，基于 Jackson
     **/
    @JsonValue
    private final String value;
    /**
     * 实际数据库字段名称
     **/
    private final String name;

    /**
     * 构造方法，默认私有
     **/
    DefaultSortColumnEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据枚举值寻找枚举对象
     * <p>
     * {@link JsonCreator} 指定使用此方法进行反序列化，基于Jackson
     *
     * @return 找到的枚举对象，没找到抛出异常
     * @throws com.lwh147.common.core.exception.CommonException
     **/
    @JsonCreator
    public static DefaultSortColumnEnum fromValue(String value) {
        for (DefaultSortColumnEnum e : DefaultSortColumnEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw CommonExceptionEnum.CLIENT_ARGUMENT_NOT_VALID_ERROR.toException("[" + value + "]不是允许的排序字段");
    }

    /**
     * 判断枚举值是否存在
     *
     * @return 匹配的枚举对象，不存在返回 {@code null}
     **/
    public static DefaultSortColumnEnum exist(String value) {
        for (DefaultSortColumnEnum e : DefaultSortColumnEnum.values()) {
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
