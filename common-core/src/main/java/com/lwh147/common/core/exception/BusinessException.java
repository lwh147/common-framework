package com.lwh147.common.core.exception;

import com.sun.istack.internal.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * 自定义业务异常
 *
 * @author lwh
 * @date 2021/11/8 10:25
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException implements ICommonException {
    /**
     * 错误来源
     **/
    private String source;
    /**
     * 错误码
     **/
    private String code;
    /**
     * 错误信息
     **/
    private String message;
    /**
     * 错误原因
     **/
    @Nullable
    private String causation;

    /**
     * 基本构造方式
     *
     * @param code    错误码
     * @param message 错误信息
     **/
    public BusinessException(String code, String message) {
        super(MessageFormat.format(MESSAGE_PATTERN, code, message, null));
        this.code = code;
        this.message = message;
    }

    /**
     * 带错误原因的构造方式
     *
     * @param code      错误码
     * @param message   错误信息
     * @param causation 错误原因
     **/
    public BusinessException(String code, String message, String causation) {
        super(MessageFormat.format(MESSAGE_PATTERN, code, message, causation));
        this.code = code;
        this.message = message;
        this.causation = causation;
    }

    /**
     * 使用异常枚举类行为规范接口对象的构造方式
     *
     * @param ice 异常枚举类行为规范接口对象
     **/
    public BusinessException(ICommonExceptionEnum ice) {
        super(MessageFormat.format(MESSAGE_PATTERN, ice.getCode(), ice.getMessage(), null));
        this.code = ice.getCode();
        this.message = ice.getMessage();
    }

    /**
     * 使用异常枚举类行为规范接口对象的构造方式
     * 带错误原因
     *
     * @param ice       异常枚举类行为规范接口对象
     * @param causation 错误原因
     **/
    public BusinessException(ICommonExceptionEnum ice, String causation) {
        super(MessageFormat.format(MESSAGE_PATTERN, ice.getCode(), ice.getMessage(), causation));
        this.code = ice.getCode();
        this.message = ice.getMessage();
        this.causation = causation;
    }

    /**
     * 使用异常枚举类行为规范接口对象的构造方式
     * 带错误原因
     *
     * @param ice 异常枚举类行为规范接口对象
     * @param e   错误原因，导致出错的未知异常
     **/
    public BusinessException(ICommonExceptionEnum ice, Throwable e) {
        super(MessageFormat.format(MESSAGE_PATTERN, ice.getCode(), ice.getMessage(), e.getMessage()), e);
        this.code = ice.getCode();
        this.message = ice.getMessage();
        this.causation = e.getMessage();
    }

    /**
     * 转String字符串模板
     **/
    private static final String MESSAGE_PATTERN = "BusinessException(code: {0}, message: {1}, causation: {2})";
}
