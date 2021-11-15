package com.lwh147.common.core.exception;

import com.sun.istack.internal.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 使用异常枚举类行为规范接口对象的基本构造方式
     * <p>
     * 只包含简单的错误码和错误信息
     *
     * @param ice 异常枚举类行为规范接口对象
     **/
    public BusinessException(ICommonExceptionEnum ice) {
        super();
        this.code = ice.getCode();
        this.message = ice.getMessage();
    }

    /**
     * 使用异常枚举类行为规范接口对象的完整构造方式1
     * <p>
     * 包含错误码、错误信息以及错误原因描述
     *
     * @param ice       异常枚举类行为规范接口对象
     * @param causation 错误原因
     **/
    public BusinessException(ICommonExceptionEnum ice, String causation) {
        super();
        this.code = ice.getCode();
        this.message = ice.getMessage();
        this.causation = causation;
    }

    /**
     * 使用异常枚举类行为规范接口对象的完整构造方式2
     * <p>
     * 包含错误码、错误信息以及错误原因描述
     * <p>
     * 与方式1不同的是错误原因默认使用从 传入的 导致出错的异常对象中获取的信息
     *
     * @param ice 异常枚举类行为规范接口对象
     * @param e   导致出错的异常对象
     **/
    public BusinessException(ICommonExceptionEnum ice, Throwable e) {
        super(e);
        this.code = ice.getCode();
        this.message = ice.getMessage();
        this.causation = e.getMessage();
    }

    /**
     * 使用异常枚举类行为规范接口对象的完整构造方式3
     * <p>
     * 包含错误码、错误信息以及错误原因描述
     * <p>
     * 在方式2的基础上增加用户自定义错误原因的支持（比如汉化或友好化）
     *
     * @param ice      异常枚举类行为规范接口对象
     * @param casation 错误原因描述
     * @param e        导致出错的异常对象
     **/
    public BusinessException(ICommonExceptionEnum ice, String casation, Throwable e) {
        super(e);
        this.code = ice.getCode();
        this.message = ice.getMessage();
        this.causation = casation;
    }
}
