package com.lwh147.common.web.exception.converter;

import com.lwh147.common.core.exception.ICommonException;

/**
 * 异常转换器行为规范接口
 *
 * @param <T> 继承自Exception
 * @author lwh
 * @date 2021/11/10 15:40
 **/
public interface IExceptionConverter<T extends Exception> {
    /**
     * 转换方法，将接受的异常类转换为自定义异常类型
     *
     * @param e 异常对象
     * @return 自定义异常类型
     **/
    ICommonException convert(T e);
}
