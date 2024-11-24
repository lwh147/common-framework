package c.l.c.test.exception;

import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.core.exception.EnhancedExceptionConverter;
import com.lwh147.common.core.exception.EnhancedRuntimeException;

/**
 * 增强异常转换功能测试，将空指针异常转换
 *
 * @author lwh
 * @date 2022/5/31 11:32
 **/
public class NullPointerExceptionConverter implements EnhancedExceptionConverter<NullPointerException> {
    @Override
    public EnhancedRuntimeException convert(NullPointerException e) {
        return CommonExceptionEnum.SYSTEM_ERROR.toException("空指针异常", e);
    }
}