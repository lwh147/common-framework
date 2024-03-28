package com.lwh147.common.core.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * 排序规则枚举
 *
 * @author lwh
 * @date 2021/11/9 10:30
 **/
@Getter
@ApiModel(description = "排序规则枚举")
public enum SortOrderEnum implements ValueNameEnum<String> {

    ASC("asc", "升序"),
    DESC("desc", "降序"),
    ;

    private final String value;
    private final String name;

    SortOrderEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return value + "-" + name;
    }
}