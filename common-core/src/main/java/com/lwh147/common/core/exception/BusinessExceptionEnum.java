package com.lwh147.common.core.exception;

/**
 * 自定义业务异常枚举示例
 *
 * @author lwh
 * @date 2021/11/8 10:38
 **/
public enum BusinessExceptionEnum implements ICommonExceptionEnum {
    /**
     * 推荐按照 “ B + 服务名首字母大写 + 表名首字母大写 + 两位数字 ” 的形式依次定义异常码
     **/
    BUSINESS_ERROR("B0000", "业务异常"),

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
    public BusinessException toException(String causation, Throwable e) {
        return new BusinessException(this, causation, e);
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
