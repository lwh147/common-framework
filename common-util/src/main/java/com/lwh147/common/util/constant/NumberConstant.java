package com.lwh147.common.util.constant;

/**
 * 数字常量类
 *
 * @author lwh
 * @date 2021/11/17 14:57
 **/
public class NumberConstant {
    /**
     * 默认缓冲区大小
     **/
    public static final int DEFAULT_BUFFERED_SIZE = 8196;
    /**
     * 装载因子，可用作内存、数组容量、CPU使用率等阈值
     **/
    public static final float DEFAULT_LOAD_FACTOR = 0.85f;
    /**
     * 2M大小
     **/
    public static final long M2 = 2 * 1024 * 1024;

    private NumberConstant() {
        throw new IllegalStateException("Constant class");
    }
}