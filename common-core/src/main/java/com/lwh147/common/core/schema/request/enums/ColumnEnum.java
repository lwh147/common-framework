package com.lwh147.common.core.schema.request.enums;

import com.fasterxml.jackson.annotation.JsonValue;

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
    @JsonValue
    String getParamName();

    /**
     * 获取实际表列名
     *
     * @return 实际表列名
     **/
    String getColumnName();

    /**
     * 获取排序字段名称
     *
     * @return 排序字段名称
     **/
    String getName();

    /**
     * 推荐按 paramName-name 格式输出以提高日志和接口文档的可读性，还有安全性
     * <p>
     * 推荐重写原因见 {@link com.lwh147.common.core.enums.Enum#toString()}
     *
     * @apiNote 不输出 {@code columnName} 是为了避免暴露数据库表列名
     **/
    String toString();
}