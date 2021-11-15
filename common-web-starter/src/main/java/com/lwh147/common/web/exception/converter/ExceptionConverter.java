package com.lwh147.common.web.exception.converter;

import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.core.exception.ICommonException;

/**
 * 其它可检查异常转换器
 *
 * @author lwh
 * @date 2021/11/15 17:50
 **/
public class ExceptionConverter implements IExceptionConverter<Exception> {
    @Override
    public ICommonException convert(Exception e) {
        return CommonExceptionEnum.SYSTEM_ERROR.toException("其它异常[" + e.getClass().toString() + "]: "
                + e.getMessage(), e);
    }
}
