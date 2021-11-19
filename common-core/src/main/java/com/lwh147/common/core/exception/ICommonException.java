package com.lwh147.common.core.exception;

/**
 * 自定义异常行为规范接口
 *
 * @author lwh
 * @date 2021/11/8 10:48
 **/
public interface ICommonException {
    /**
     * 必须重写toString
     *
     * @return String
     **/
    @Override
    String toString();

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

    /**
     * 获取错误原因
     *
     * @return String
     **/
    String getCausation();

    /**
     * 设置错误原因
     *
     * @param causation 错误原因
     **/
    void setCausation(String causation);

    /**
     * 获取错误来源
     *
     * @return String
     **/
    String getSource();

    /**
     * 设置错误来源
     *
     * @param source 错误来源
     **/
    void setSource(String source);

    /**
     * 获取导致出错的异常对象
     *
     * @return Throwable 导致出错的异常对象
     **/
    Throwable getCause();
}
