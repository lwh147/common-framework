package com.lwh147.common.core.exception;

/**
 * 通用开发框架层异常类
 *
 * @author lwh
 * @date 2021/10/22 10:47
 **/
public final class CommonException extends EnhancedRuntimeException {

    CommonException(EnhancedRuntimeExceptionEnum e) {
        super(e);
    }

    CommonException(EnhancedRuntimeExceptionEnum e, String message) {
        super(e, message);
    }

    CommonException(EnhancedRuntimeExceptionEnum e, Throwable cause) {
        super(e, cause);
    }

    CommonException(EnhancedRuntimeExceptionEnum e, String message, Throwable cause) {
        super(e, message, cause);
    }
}