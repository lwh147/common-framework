package com.lwh147.common.web.logger.filter;


import com.lwh147.common.model.constant.HttpConstant;
import com.lwh147.common.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 可重复读请求对象替换过滤器
 * <p>
 * 要对request的行为进行更改，只能通过过滤器实现
 *
 * @author lwh
 * @date 2021/11/17 10:11
 **/
@Component
public class RequestReplaceFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            if (this.isRepeatableSupport(httpServletRequest)) {
                // 替换请求对象为可重复读的请求对象，拦截器做不到
                filterChain.doFilter(new RepeatableReadRequestWrapper(httpServletRequest), servletResponse);
            } else {
                // 不是可替换请求类型，通行
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            // 不是HttpServletRequest，通行
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 判断该请求是否需要替换为可重复读请求
     *
     * @param httpServletRequest 请求对象
     * @return 判断结果
     **/
    private boolean isRepeatableSupport(HttpServletRequest httpServletRequest) {
        String contentType = httpServletRequest.getContentType();
        if (Strings.isBlank(contentType)) {
            return false;
        }
        // 默认使用JSON数据作为交互数据格式
        return contentType.contains(HttpConstant.ContentType.APPLICATION_JSON);
    }

    @Override
    public void destroy() {
    }
}