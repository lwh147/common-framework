package com.lwh147.common.core.enums.column;

/**
 * 表列名枚举类行为规范接口
 *
 * @author lwh
 * @date 2021/11/1 15:40
 **/
public interface IColumnEnum {
    /**
     * 获取枚举值
     *
     * @return 枚举值
     **/
    String getValue();

    /**
     * 获取对应表列名
     *
     * @return 表列名
     **/
    String getName();

    /**
     * 应该返回枚举值，保持与序列化结果的一致性
     * <p>
     * 枚举类的 {@link Enum#toString()} 默认返回枚举对象的 {@code name}
     *
     * @return 枚举值
     **/
    @Override
    String toString();
}
