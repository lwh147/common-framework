package com.lwh147.common.core.enums;

/**
 * 表列名枚举类行为规范接口
 *
 * @author lwh
 * @date 2021/11/1 15:40
 **/
public interface ColumnEnum {
    /**
     * 获取参数名称
     *
     * @return 参数名称
     **/
    String getParamName();

    /**
     * 获取实际表列名
     *
     * @return 实际表列名
     **/
    String getColumnName();

    /**
     * 获取排序字段名
     *
     * @return 排序字段名
     **/
    String getName();
}