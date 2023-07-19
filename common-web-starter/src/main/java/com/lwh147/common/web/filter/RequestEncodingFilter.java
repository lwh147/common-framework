package com.lwh147.common.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 统一编码过滤器
 *
 * @author lwh
 * @date 2021/11/18 15:22
 **/
@Component
public class RequestEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * 统一编码
     **/
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 设置编码格式为UTF-8
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}