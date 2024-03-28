package com.lwh147.common.core.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * 基础的可排序表列名枚举
 *
 * @author lwh
 * @date 2021/12/12 23:17
 **/
@Getter
@ApiModel(description = "基础的可排序表列明枚举")
public enum CommonSortColumnEnum implements DbColumnEnum {

    CREATE_TIME("createTime", "create_time", "创建时间"),
    UPDATE_TIME("updateTime", "update_time", "更新时间"),
    ;

    private final String paramName;
    private final String columnName;
    private final String name;

    CommonSortColumnEnum(String paramName, String columnName, String name) {
        this.paramName = paramName;
        this.columnName = columnName;
        this.name = name;
    }

    @Override
    public String toString() {
        return paramName + "-" + name;
    }
}