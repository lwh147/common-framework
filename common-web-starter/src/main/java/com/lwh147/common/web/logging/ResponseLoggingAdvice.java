package com.lwh147.common.web.logging;

import com.lwh147.common.core.context.ContextHolder;
import com.lwh147.common.core.response.RespBody;
import com.lwh147.common.model.constant.HttpConstant;
import com.lwh147.common.util.JacksonUtils;
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

/**
 * 响应日志记录器的切面
 *
 * @author lwh
 * @date 2021/11/17 9:43
 **/
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ResponseLoggingAdvice implements ResponseBodyAdvice<Object> {
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
        // 从上下文中获取基础请求信息
        String requestMethod = ContextHolder.get(HttpConstant.REQUEST_METHOD);
        String requestUrl = ContextHolder.get(HttpConstant.REQUEST_URL);
        // ===> 代表响应
        String template = "==> {} {}\n";
        String requestBody = ContextHolder.get(HttpConstant.REQUEST_BODY);
        String responseBody = JacksonUtils.toJsonStr(body);
        if (requestBody != null) {
            template += HttpConstant.REQUEST_BODY + ": {}\n" + HttpConstant.RESPONSE_BODY + ": {}\n";
            log.info(template, String.format("%6s", requestMethod), requestUrl, requestBody, responseBody);
            return;
        }
        template += HttpConstant.RESPONSE_BODY + ": {}\n";
        log.info(template, String.format("%6s", requestMethod), requestUrl, responseBody);
    }
}