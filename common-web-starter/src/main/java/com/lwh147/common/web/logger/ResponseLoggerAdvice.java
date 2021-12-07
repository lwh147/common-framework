package com.lwh147.common.web.logger;

import com.lwh147.common.core.constant.WebConstant;
import com.lwh147.common.util.JacksonUtil;
import com.lwh147.common.web.logger.context.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * 响应日志记录器的切面
 *
 * @author lwh
 * @date 2021/11/17 9:43
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.lwh147")
public class ResponseLoggerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@Nonnull MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nonnull MethodParameter returnType, @Nonnull MediaType selectedContentType, @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        // 打印日志
        this.doLog(body);
        return body;
    }

    /**
     * 响应日志记录
     *
     * @param body 响应内容
     **/
    private void doLog(Object body) {
        // 从上下文中获取基础请求信息
        String requestMethod = ContextHolder.get(WebConstant.REQUEST_METHOD);
        String requestUrl = ContextHolder.get(WebConstant.REQUEST_URL);
        // ===> 代表响应
        String template = "===> {} {}";
        String requestBody = ContextHolder.get(WebConstant.REQUEST_BODY);
        String responseBody = JacksonUtil.toJSON(body);
        if (Objects.nonNull(requestBody)) {
            template += "\n" + WebConstant.REQUEST_BODY + ": {}\n" + WebConstant.RESPONSE_BODY + ": {}";
            log.info(template, String.format("%6s", requestMethod), requestUrl, requestBody, responseBody);
            return;
        }
        template += "\n" + WebConstant.RESPONSE_BODY + ": {}";
        log.info(template, String.format("%6s", requestMethod), requestUrl, responseBody);
    }
}
