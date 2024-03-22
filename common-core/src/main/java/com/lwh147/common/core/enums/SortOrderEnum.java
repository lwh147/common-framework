package com.lwh147.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 排序规则枚举
 *
 * @author lwh
 * @date 2021/11/9 10:30
 **/
public enum SortOrderEnum implements ValueNameEnum {

    ASC("ASC", "升序"),
    DESC("DESC", "降序"),
    ;

    /**
     * @apiNote {@link JsonValue} 可指定序列化时使用该方法结果作为枚举值，和 {@link JsonValue} 关联使用，基于 Jackson
     * <p>
     * {@link EnumValue} 可指定数据入库时使用该属性作为枚举值，基于 MybatisPlus，这里仅作示例
     **/
    @EnumValue
    private final String value;
    private final String name;

    SortOrderEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return value + "-" + name;
    }
}