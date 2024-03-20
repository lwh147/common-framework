package com.lwh147.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 排序规则枚举
 *
 * @author lwh
 * @date 2021/11/9 10:30
 **/
public enum SortOrderEnum implements Enum {

    ASC("ASC", "升序"),
    DESC("DESC", "降序"),
    ;

    /**
     * @apiNote {@link EnumValue} 可指定数据入库时使用该属性作为枚举值，基于 MybatisPlus，这里仅作示例用
     **/
    @EnumValue
    private final String value;
    private final String name;

    SortOrderEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据枚举值寻找枚举对象，推荐实现
     *
     * @return 找到的枚举对象，没找到返回 {@code null}
     * @apiNote {@link JsonCreator} 可指定使用此方法进行反序列化，基于Jackson
     **/
    @JsonCreator
    public static SortOrderEnum from(String value) {
        for (SortOrderEnum e : SortOrderEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 判断枚举值是否存在，推荐实现
     *
     * @return 枚举值是否存在
     **/
    public static boolean exist(String value) {
        return from(value) != null;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 推荐按此格式重写toString()方法
     **/
    @Override
    public String toString() {
        return this.value + "-" + this.name;
    }
}