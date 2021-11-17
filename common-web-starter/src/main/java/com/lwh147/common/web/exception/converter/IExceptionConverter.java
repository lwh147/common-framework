package com.lwh147.common.web.exception.converter;

import com.lwh147.common.core.exception.ICommonException;

/**
 * 异常转换器行为规范接口
 *
 * @author lwh
 * @date 2021/11/10 15:40
 **/
public interface IExceptionConverter<T extends Exception> {
    /**
     * 转换方法，将捕获到的异常类转换为自定义异常
     *
     * @param e 异常对象
     * @return ICommonException
     **/
    ICommonException convert(T e);
}