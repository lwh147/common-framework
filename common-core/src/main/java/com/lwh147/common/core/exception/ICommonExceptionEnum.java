package com.lwh147.common.core.exception;

/**
 * 自定义异常枚举行为规范接口
 *
 * @author lwh
 * @date 2021/10/27 16:55
 **/
public interface ICommonExceptionEnum {
    /**
     * 直接根据枚举类构造异常
     *
     * @return ICommonException
     **/
    ICommonException toException();

    /**
     * 构造带错误原因的异常
     *
     * @param causation 错误原因
     * @return ICommonException
     **/
    ICommonException toException(String causation);

    /**
     * 构造带导致错误的原始异常对象的异常
     *
     * @param e 导致发生错误的未知异常
     * @return ICommonException
     **/
    ICommonException toException(Throwable e);

    /**
     * 获取错误码
     *
     * @return String
     **/
    String getCode();

    /**
     * 获取错误信息
     *
     * @return String
     **/
    String getMessage();
}
