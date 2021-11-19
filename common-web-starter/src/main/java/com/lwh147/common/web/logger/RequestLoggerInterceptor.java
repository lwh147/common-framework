package com.lwh147.common.web.logger;

import com.lwh147.common.web.logger.filter.RepeatableReadRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 请求日志记录器的拦截器
 *
 * @author lwh
 * @date 2021/11/17 9:48
 **/
@Slf4j
@Component
public class RequestLoggerInterceptor implements HandlerInterceptor {
    public RequestLoggerInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        // 打印请求信息
        this.doLog(request);
        return true;
    }

    /**
     * 请求日志记录
     *
     * @param request 请求对象
     **/
    private void doLog(HttpServletRequest request) {
        // 获取基础请求信息
        String method = request.getMethod();
        String queryString = request.getQueryString();
        String url = request.getRequestURL().toString() + (Objects.isNull(queryString) ? "" : ("?" + queryString));
        // 基础打印模板，请求方法 请求url
        String template = "===> {} {}";
        // 是否是包装类型
        if (request instanceof RepeatableReadRequestWrapper) {
            // 获取请求体信息
            String bodyString = ((RepeatableReadRequestWrapper) request).getBody();
            // 不为空时追加请求体信息打印
            if (Objects.nonNull(bodyString)) {
                template += " {}";
                log.info(template, method, url, bodyString);
                return;
            }
        }
        log.info(template, method, url);
    }

    @Override
    public void postHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, ModelAndView modelAndView) throws Exception {
        this.doLog(response);
    }

    /**
     * 响应日志记录
     *
     * @param response 响应对象
     **/
    private void doLog(HttpServletResponse response) {

    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) throws Exception {
    }
}
