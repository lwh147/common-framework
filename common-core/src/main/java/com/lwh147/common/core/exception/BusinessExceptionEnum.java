package com.lwh147.common.core.exception;

/**
 * 自定义业务异常枚举类
 *
 * @author lwh
 * @date 2021/11/8 10:38
 **/
public enum BusinessExceptionEnum implements ICommonExceptionEnum {
    /**
     * 占位
     **/
    CONNECTION_REFUSED("C200004", "连接被拒绝"),

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
    BusinessExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public BusinessException toException() {
        return new BusinessException(this);
    }

    @Override
    public BusinessException toException(String causation) {
        return new BusinessException(this, causation);
    }

    @Override
    public BusinessException toException(Throwable e) {
        return new BusinessException(this, e);
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
