package c.l.c.test.exception;

import com.lwh147.common.core.exception.ICommonException;
import com.lwh147.common.web.exception.IExceptionConverter;

/**
 * TODO
 *
 * @author lwh
 * @date 2022/5/31 11:32
 **/
public class TestExceptionConverter implements IExceptionConverter<NullPointerException> {
    @Override
    public ICommonException convert(NullPointerException e) {
        return null;
    }
}