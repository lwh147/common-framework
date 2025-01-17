package com.lwh147.common.web.logging;

import com.lwh147.common.core.schema.response.RespBody;
import com.lwh147.common.util.JacksonUtils;
import com.lwh147.common.web.properties.WebProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * 响应日志记录器的切面
 *
 * @author lwh
 * @date 2021/11/17 9:43
 **/
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ResponseLoggingAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private WebProperties webProperties;

    @Override
    public boolean supports(@Nonnull MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nonnull MethodParameter returnType, @Nonnull MediaType selectedContentType, @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        // 打印日志
        this.doLog(body);
        return body instanceof RespBody ? body : RespBody.success(body);
    }

    /**
     * 响应日志记录
     *
     * @param body 响应内容
     **/
    private void doLog(Object body) {
        Integer length = webProperties.getRequestParamsAndBodyPrintLength();
        if (length == 0) {
            return;
        }
        String bodyStr = JacksonUtils.toJsonStr(body);
        if (length > 0) {
            log.info("==>{}: {}", String.format("%11s", "Body"), bodyStr == null ? null : bodyStr.length() < length ?
                    bodyStr : (bodyStr.substring(0, length) + "...Truncated"));
        } else {
            log.info("==>{}: {}", String.format("%11s", "Body"), bodyStr);
        }
    }
}