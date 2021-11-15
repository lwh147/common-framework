package com.lwh147.common.web.exception.converter;

import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.core.exception.ICommonException;

/**
 * 其它不可检查异常转换器
 *
 * @author lwh
 * @date 2021/11/11 10:36
 **/
public class RuntimeExceptionConverter implements IExceptionConverter<RuntimeException> {
    @Override
    public ICommonException convert(RuntimeException e) {
        return CommonExceptionEnum.SYSTEM_ERROR.toException("其它运行时异常[" + e.getClass().toString() + "]: "
                + e.getMessage(), e);
    }
}
