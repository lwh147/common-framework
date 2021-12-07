package com.lwh147.common.core.exception;

/**
 * 自定义异常行为规范接口
 *
 * @author lwh
 * @date 2021/11/8 10:48
 **/
public interface ICommonException {
    /**
     * 复用了父类属性，所以必须重写 {@link Object#toString()}
     *
     * @return 完整的异常信息
     **/
    @Override
    String toString();

    /**
     * 获取错误来源
     *
     * @return 错误来源
     **/
    String getSource();

    /**
     * 设置错误来源
     *
     * @param source 错误来源
     **/
    void setSource(String source);

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

    /**
     * 获取错误原因
     *
     * @return 错误原因，一个异常对象
     **/
    Throwable getCause();

    /**
     * 获取错误详情
     *
     * @return 错误原因的详细描述
     **/
    String getMessage();
}
