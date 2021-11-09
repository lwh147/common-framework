package com.lwh147.common.core.enums;

/**
 * 枚举类行为规范接口
 * <p>
 * 由于枚举类的特殊性，方法 {@code fromValue()} 和 {@code exist()}
 * 虽然也必须存在于枚举类中，但不能通过该行为规范接口进行强制性约束
 *
 * @author lwh
 * @date 2021/11/1 15:40
 **/
public interface ICommonEnum {
    /**
     * 获取枚举值
     *
     * @return String
     **/
    String getValue();

    /**
     * 获取枚举名称
     *
     * @return String
     **/
    String getName();
}
