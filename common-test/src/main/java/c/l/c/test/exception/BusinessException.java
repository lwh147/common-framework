package c.l.c.test.exception;

import com.lwh147.common.core.exception.EnhancedRuntimeException;
import com.lwh147.common.core.exception.EnhancedRuntimeExceptionEnum;

/**
 * 自定义业务异常
 *
 * @author lwh
 * @date 2021/11/8 10:25
 **/
public class BusinessException extends EnhancedRuntimeException {

    public BusinessException(EnhancedRuntimeExceptionEnum e) {
        super(e);
    }

    public BusinessException(EnhancedRuntimeExceptionEnum e, String message) {
        super(e, message);
    }

    public BusinessException(EnhancedRuntimeExceptionEnum e, Throwable cause) {
        super(e, cause);
    }

    public BusinessException(EnhancedRuntimeExceptionEnum e, String message, Throwable cause) {
        super(e, message, cause);
    }
}