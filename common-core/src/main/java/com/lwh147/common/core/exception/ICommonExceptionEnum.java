package com.lwh147.common.core.exception;

/**
 * 自定义异常枚举行为规范接口
 *
 * @author lwh
 * @date 2021/10/27 16:55
 **/
public interface ICommonExceptionEnum {
    /**
     * 直接根据枚举类构造异常，对应基本构造方式
     *
     * @return ICommonException
     **/
    ICommonException toException();

    /**
     * 构造带错误详情的异常，对应较完整的构造方式
     *
     * @param detailMessage 错误原因的详细描述
     * @return ICommonException
     **/
    ICommonException toException(String detailMessage);

    /**
     * 构造带错误原因的异常，对应完整构造方式1
     *
     * @param cause 错误原因，一个异常对象
     * @return ICommonException
     **/
    ICommonException toException(Throwable cause);

    /**
     * 构造带错误原因和错误详情的异常，对应完整构造方式2
     *
     * @param detailMessage 错误原因的详细描述
     * @param cause         错误原因，一个异常对象
     * @return ICommonException
     **/
    ICommonException toException(String detailMessage, Throwable cause);

    /**
     * 获取错误码
     *
     * @return 错误码
     **/
    String getCode();

    /**
     * 获取错误描述
     *
     * @return 错误描述
     **/
    String getDescription();
}