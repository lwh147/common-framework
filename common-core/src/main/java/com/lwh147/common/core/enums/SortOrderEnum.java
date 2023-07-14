package com.lwh147.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 排序规则枚举
 *
 * @author lwh
 * @date 2021/11/9 10:30
 **/
public enum SortOrderEnum implements Enum {
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
     **/
    @JsonValue
    private final String value;
    /**
     * 枚举值名称
     **/
    private final String name;

    SortOrderEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据枚举值寻找枚举对象
     *
     * @return 找到的枚举对象，没找到返回 {@code null}
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
     * 判断枚举值是否存在
     *
     * @return 枚举值是否存在
     **/
    public static boolean exist(String value) {
        return from(value) == null;
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
        return this.value + "-" + this.name;
    }
}