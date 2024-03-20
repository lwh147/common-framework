package com.lwh147.common.core.schema.request.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 基础的可排序表列名枚举类
 *
 * @author lwh
 * @date 2021/12/12 23:17
 **/
public enum BaseSortColumnEnum implements ColumnEnum {
    /**
     * 创建时间
     **/
    CREATE_TIME("createTime", "create_time", "创建时间"),
    ;

    /**
     * 暴露给前端的参数名
     *
     * @apiNote 这样使用能够避免暴露数据库表列名称到前端，增加安全性
     **/
    private final String paramName;
    /**
     * 实际数据库表列名
     **/
    private final String columnName;
    /**
     * 排序字段名称
     **/
    private final String name;

    BaseSortColumnEnum(String paramName, String columnName, String name) {
        this.paramName = paramName;
        this.columnName = columnName;
        this.name = name;
    }

    /**
     * 根据参数名称寻找可排序表列名枚举对象
     *
     * @return 匹配的枚举对象，没找到返回 {@code null}
     **/
    @JsonCreator
    public static BaseSortColumnEnum from(String paramName) {
        for (BaseSortColumnEnum e : BaseSortColumnEnum.values()) {
            if (e.getParamName().equals(paramName)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 判断排序参数对应的排序枚举对象是否存在
     *
     * @return 是否存在排序参数对应的排序枚举对象
     **/
    public static boolean exist(String paramName) {
        return from(paramName) == null;
    }

    @Override
    public String getParamName() {
        return this.paramName;
    }

    @Override
    public String getColumnName() {
        return this.columnName;
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
        return this.paramName + "-" + this.name;
    }
}