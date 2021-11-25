package com.lwh147.common.core.exception;

import com.lwh147.common.core.constant.RegExpConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * 自定义业务异常
 * <p>
 * 详细解释说明参考自定义非业务异常 {@link CommonException}
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
     * 错误描述
     **/
    private String description;

    /**
     * 基本构造方式，不推荐
     * <p>
     * 只包含简单的错误码和错误描述
     * <p>
     * 属于第一种嵌套异常情况，自身就是错误原因
     * <p>
     * 错误详情与错误描述一致
     *
     * @param ice 异常枚举类行为规范接口对象
     **/
    public BusinessException(ICommonExceptionEnum ice) {
        // 没有错误详情所以使用错误描述作为错误详情
        // 默认cause=this
        super(ice.getDescription());
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    /**
     * 较为完整的构造方式，推荐
     * <p>
     * 包含错误码、错误描述以及错误详情
     * <p>
     * 属于第一种嵌套异常情况，自身就是错误原因
     *
     * @param ice           异常枚举类行为规范接口对象
     * @param detailMessage 错误原因的详细描述
     **/
    public BusinessException(ICommonExceptionEnum ice, String detailMessage) {
        // 默认cause=this
        super(detailMessage);
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    /**
     * 完整构造方式1
     * <p>
     * 包含错误码、错误描述以及错误原因
     * <p>
     * 适用于第二种嵌套异常情况，带有错误原因
     * <p>
     * 错误详情使用错误原因对象的原始信息
     *
     * @param ice   异常枚举类行为规范接口对象
     * @param cause 错误原因，一个异常对象
     **/
    public BusinessException(ICommonExceptionEnum ice, Throwable cause) {
        // 去除获取到的message中可能存在的格式化字符
        super(RegExpConstant.ENTER_PATTERN.matcher(cause.getMessage()).replaceAll(""), cause);
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    /**
     * 完整构造方式2，推荐
     * <p>
     * 包含错误码、错误描述、错误原因以及错误详情
     * <p>
     * 适用于第二种嵌套异常情况，带有错误原因
     *
     * @param ice           异常枚举类行为规范接口对象
     * @param detailMessage 错误原因的详细描述
     * @param cause         错误原因，一个异常对象
     **/
    public BusinessException(ICommonExceptionEnum ice, String detailMessage, Throwable cause) {
        super(detailMessage, cause);
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    @Override
    public String toString() {
        return MessageFormat.format("BusinessException(source={0}, code={1}, description={2}, detailMessage={3})",
                this.source, this.code, this.description, super.getMessage());
    }
}