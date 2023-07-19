package com.lwh147.common.web.logging;

import com.lwh147.common.core.context.ContextHolder;
import com.lwh147.common.model.constant.HttpConstant;
import com.lwh147.common.web.filter.RepeatableReadRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求日志记录器的拦截器
 *
 * @author lwh
 * @date 2021/11/17 9:48
 **/
@Slf4j
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    public RequestLoggingInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
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
        // 获取基础请求信息并保存到上下文中方便后续使用
        String method = request.getMethod();
        ContextHolder.set(HttpConstant.REQUEST_METHOD, method);
        String param = request.getQueryString();
        String url = request.getRequestURL().toString() + (param == null ? "" : ("?" + param));
        ContextHolder.set(HttpConstant.REQUEST_URL, url);
        // 基础打印模板，<===代表收到请求 请求方法 请求url
        String template = "<== {} {}";
        // 是否是包装类型
        if (request instanceof RepeatableReadRequestWrapper) {
            // 获取请求体信息
            String requestBody = ((RepeatableReadRequestWrapper) request).getBody();
            // 不为空时追加请求体信息打印
            if (requestBody != null) {
                template += "\n" + HttpConstant.REQUEST_BODY + ": {}\n";
                log.info(template, String.format("%6s", method), url, requestBody);
                // 同时写入上下文
                ContextHolder.set(HttpConstant.REQUEST_BODY, requestBody);
                return;
            }
        }
        log.info(template, String.format("%6s", method), url);
    }

    @Override
    public void postHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, ModelAndView modelAndView) {
        // 响应信息日志记录不能放这里，因为不能获取响应体
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) {
        // 请求已完成，清除保存的上下文信息
        ContextHolder.remove();
    }
}