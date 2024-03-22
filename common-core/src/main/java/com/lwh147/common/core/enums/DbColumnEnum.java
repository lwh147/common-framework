package com.lwh147.common.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lwh147.common.core.enums.serialization.DbColumnEnumDeserializer;
import com.lwh147.common.core.enums.serialization.DbColumnEnumSerializer;

/**
 * 表列名枚举类行为规范接口
 * <p>
 * 需要将表列名作为参数传给前端时可以考虑按此接口实现枚举，避免数据库字段直接暴露给前端，增加安全性
 *
 * @author lwh
 * @date 2021/11/1 15:40
 **/
@JsonSerialize(using = DbColumnEnumSerializer.class)
@JsonDeserialize(using = DbColumnEnumDeserializer.class)
public interface DbColumnEnum {
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
     * 获取排序字段名称
     *
     * @return 排序字段名称
     **/
    String getName();

    /**
     * 强烈推荐按 paramName-name 格式输出以提高日志和接口文档的可读性，还有安全性
     * <p>
     * 见 {@link ValueNameEnum#toString()}
     *
     * @apiNote 不输出 {@code columnName} 是为了避免暴露数据库表列名
     **/
    String toString();

    /**
     * 根据枚举值寻找枚举对象
     *
     * @param <T> 实现了此接口的枚举类型
     * @return 找到的枚举对象，没找到返回 {@code null}
     **/
    static <T extends Enum<T> & DbColumnEnum> T from(Class<T> enumType, String paramName) {
        for (T e : enumType.getEnumConstants()) {
            if (e.getParamName().equals(paramName)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 判断枚举值是否存在
     *
     * @param <T> 实现了此接口的枚举类型
     * @return 枚举值是否存在
     **/
    static <T extends Enum<T> & DbColumnEnum> boolean exist(Class<T> enumType, String paramName) {
        return from(enumType, paramName) != null;
    }

    /**
     * 泛型toString
     *
     * @param <T> 实现了此接口的枚举类型
     **/
    static <T extends Enum<T> & DbColumnEnum> String toString(T enumObj) {
        return enumObj.getParamName() + "-" + enumObj.getName();
    }
}