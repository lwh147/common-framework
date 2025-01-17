package com.lwh147.common.web.logging;

import com.lwh147.common.core.context.ContextHolder;
import com.lwh147.common.util.WebUtils;
import com.lwh147.common.util.constant.HttpConstant;
import com.lwh147.common.util.constant.RegExpConstant;
import com.lwh147.common.web.filter.RepeatableReadRequestWrapper;
import com.lwh147.common.web.properties.WebProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
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
    @Resource
    private WebProperties webProperties;

    public RequestLoggingInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        // 打印请求信息
        this.doLog(request);
        return true;
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

    /**
     * 请求日志记录
     *
     * @param request 请求对象
     **/
    private void doLog(HttpServletRequest request) {
        // 获取基础请求信息并保存到上下文中方便后续使用
        String method = request.getMethod();
        String uri = request.getRequestURI();
        ContextHolder.set(HttpConstant.REQUEST_URI, uri);
        String params = request.getQueryString();
        String ip = WebUtils.getIpAddr(request);
        ContextHolder.set(HttpConstant.REQUEST_IP, ip);
        String body = null;
        // 是否是包装类型
        if (request instanceof RepeatableReadRequestWrapper) {
            // 获取请求体信息
            body = ((RepeatableReadRequestWrapper) request).getBody();
            if (body != null) {
                body = RegExpConstant.ENTER_PATTERN.matcher(body).replaceAll("");
            }
        }
        log.info("<=={}: {}", String.format("%11s", method), uri);
        log.info("<=={}: {}", String.format("%11s", "From"), ip);
        Integer length = webProperties.getRequestParamsAndBodyPrintLength();
        if (length == 0) {
            return;
        }
        if (length > 0) {
            log.info("<=={}: {}", String.format("%11s", "Params"), params == null ? null : params.length() < length ?
                    params : (params.substring(0, length) + "...Truncated"));
            log.info("<=={}: {}", String.format("%11s", "Body"), body == null ? null : body.length() < length ?
                    body : (body.substring(0, length) + "...Truncated"));
        } else {
            log.info("<=={}: {}", String.format("%11s", "Params"), params);
            log.info("<=={}: {}", String.format("%11s", "Body"), body);
        }
    }
}