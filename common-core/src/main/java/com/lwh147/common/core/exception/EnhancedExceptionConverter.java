package com.lwh147.common.core.exception;

/**
 * 增强异常转换器接口，可实现此接口将常见异常转换为增强异常的接口
 *
 * @param <T> 继承自Exception
 * @author lwh
 * @date 2021/11/10 15:40
 **/
public interface EnhancedExceptionConverter<T extends Exception> {
    /**
     * 转换方法，将接受的异常类转换为增强异常类型
     *
     * @param e 异常对象
     * @return 增强异常类型
     **/
    EnhancedRuntimeException convert(T e);
}