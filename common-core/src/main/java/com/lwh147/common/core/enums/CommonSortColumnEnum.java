package com.lwh147.common.core.enums;

/**
 * 基础的可排序表列名枚举类
 *
 * @author lwh
 * @date 2021/12/12 23:17
 **/
public enum CommonSortColumnEnum implements DbColumnEnum {

    CREATE_TIME("createTime", "create_time", "创建时间"),
    UPDATE_TIME("updateTime", "update_time", "更新时间"),
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

    CommonSortColumnEnum(String paramName, String columnName, String name) {
        this.paramName = paramName;
        this.columnName = columnName;
        this.name = name;
    }

    @Override
    public String getParamName() {
        return paramName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return paramName + "-" + name;
    }
}