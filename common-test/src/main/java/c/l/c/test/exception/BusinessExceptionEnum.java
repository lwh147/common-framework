package c.l.c.test.exception;

import com.lwh147.common.core.exception.EnhancedRuntimeExceptionEnum;
import lombok.Getter;

/**
 * 自定义业务异常枚举
 *
 * @author lwh
 * @date 2021/11/8 10:38
 **/
@Getter
public enum BusinessExceptionEnum implements EnhancedRuntimeExceptionEnum {
    /**
     * 推荐按照 “ 业务英文关键字 + 三位数字 ” 的形式依次定义异常码
     **/
    BUSINESS_ERROR("BIZ000", "业务异常"),

    ;

    /**
     * 异常枚举编码
     **/
    private final String code;
    /**
     * 异常枚举名称
     **/
    private final String name;

    BusinessExceptionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public BusinessException toException() {
        return new BusinessException(this);
    }

    @Override
    public BusinessException toException(String message) {
        return new BusinessException(this, message);
    }

    @Override
    public BusinessException toException(Throwable cause) {
        return new BusinessException(this, cause);
    }

    @Override
    public BusinessException toException(String message, Throwable cause) {
        return new BusinessException(this, message, cause);
    }
}