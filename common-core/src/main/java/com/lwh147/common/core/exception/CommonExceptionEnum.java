package com.lwh147.common.core.exception;

/**
 * 自定义非业务异常枚举类
 *
 * @author lwh
 * @date 2021/10/22 10:49
 **/
public enum CommonExceptionEnum implements ICommonExceptionEnum {
    /**
     * 系统未处理的异常
     **/
    OTHER("C000000", "系统未处理的异常"),

    ;

    /**
     * 错误码
     **/
    private final String code;
    /**
     * 错误提示信息
     **/
    private final String message;

    /**
     * 构造方法，默认私有
     **/
    CommonExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public CommonException toException() {
        return new CommonException(this);
    }

    @Override
    public CommonException toException(String causation) {
        return new CommonException(this, causation);
    }

    @Override
    public CommonException toException(Throwable e) {
        return new CommonException(this, e);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
