package com.lwh147.common.core.exception;

/**
 * 继承增强异常类的异常枚举类行为规范接口，如果使用本框架提供的增强异常类则强烈推荐对应的异常枚举类实现此接口
 *
 * @author lwh
 * @date 2021/10/27 16:55
 **/
public interface EnhancedRuntimeExceptionEnum {
    /**
     * 根据枚举类构造异常
     *
     * @return EnhancedRuntimeException
     **/
    EnhancedRuntimeException toException();

    /**
     * 构造带异常信息的异常
     *
     * @param message 异常信息
     * @return EnhancedRuntimeException
     **/
    EnhancedRuntimeException toException(String message);

    /**
     * 构造带异常原因的异常
     *
     * @param cause 异常原因
     * @return EnhancedRuntimeException
     **/
    EnhancedRuntimeException toException(Throwable cause);

    /**
     * 构造带异常信息和异常原因的异常
     *
     * @param message 异常信息
     * @param cause   异常原因
     * @return EnhancedRuntimeException
     **/
    EnhancedRuntimeException toException(String message, Throwable cause);

    /**
     * 获取异常枚举编码
     *
     * @return 异常枚举编码
     **/
    String getCode();

    /**
     * 获取异常枚举名称
     *
     * @return 异常枚举名称
     **/
    String getName();
}