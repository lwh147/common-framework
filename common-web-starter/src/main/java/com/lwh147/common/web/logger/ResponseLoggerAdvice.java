package com.lwh147.common.web.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 响应日志记录器的Controller Advice
 *
 * @author lwh
 * @date 2021/11/17 9:43
 **/
@Slf4j
@RestControllerAdvice
public class ResponseLoggerAdvice {
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 打印日志
        this.doLog(body, request);
        return body;
    }

    /**
     * 响应日志记录
     *
     * @param body    响应内容
     * @param request 请求对象
     **/
    private void doLog(Object body, ServerHttpRequest request) {
        HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        // 获取基础请求信息
        String method = httpServletRequest.getMethod();
        String queryString = httpServletRequest.getQueryString();
        String url = httpServletRequest.getRequestURL().toString() + (Objects.isNull(queryString) ? "" : ("?" + queryString));

    }
}
