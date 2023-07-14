package com.lwh147.common.core.enums;

/**
 * 一般枚举类行为规范接口
 *
 * @author lwh
 * @date 2023/7/7 14:57
 **/
public interface Enum {
    /**
     * 获取枚举值
     *
     * @return 枚举值
     **/
    String getValue();

    /**
     * 获取枚举名称
     *
     * @return 枚举名称
     **/
    String getName();
}