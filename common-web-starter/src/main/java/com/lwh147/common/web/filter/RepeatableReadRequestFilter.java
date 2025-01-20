package com.lwh147.common.web.filter;


import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.util.constant.HttpConstant;
import com.lwh147.common.util.constant.NumberConstant;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 可重复读请求对象替换过滤器
 *
 * @author lwh
 * @apiNote 要对request的行为进行更改，只能通过过滤器实现
 * @date 2021/11/17 10:11
 **/
@Component
public class RepeatableReadRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            // 替换请求对象为可重复读的请求对象，默认替换JSON格式的请求体内容
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            if (HttpConstant.ContentType.APPLICATION_JSON.equals(httpServletRequest.getContentType())) {
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

    @Override
    public void destroy() {
    }

    /**
     * 可重复读的请求包装器
     *
     * @author lwh
     * @date 2021/11/17 10:08
     **/
    @Getter
    @Slf4j
    public static class RepeatableReadRequestWrapper extends HttpServletRequestWrapper {
        /**
         * 字符串，存储请求体内容
         **/
        private final String body;

        public RepeatableReadRequestWrapper(HttpServletRequest request) {
            super(request);
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = request.getReader();
                if (bufferedReader != null) {
                    // 真正读取到的数据大小
                    int charRead;
                    // 缓冲数组
                    char[] buffer = new char[NumberConstant.DEFAULT_BUFFERED_SIZE];
                    /*
                     * BufferedReader.read(char[] buffer)方法每次读取buffer.length长度的数据
                     * 当剩余数据不足length时，返回值charRead代表真正读取到的数据大小
                     */
                    while ((charRead = bufferedReader.read(buffer)) > 0) {
                        /*
                         * 写入时必须指定真正读取到的数据大小charRead进行写入，因为当整体数据大小不是buffer.length的整数倍时
                         * 最后一次读取数据时真正读取到的数据大小charRead ≠ buffer.length，buffer中 下标>charRead 的部分还是
                         * 上一次读取到的数据，所以如果不指定charRead则会将buffer中 下标>charRead 的脏数据写入结果sb中
                         *
                         * 其它方式：每次使用完buffer后buffer = new char[size]，但是比较浪费资源，不推荐
                         */
                        sb.append(buffer, 0, charRead);
                    }
                    this.body = sb.toString();
                } else {
                    this.body = null;
                }
            } catch (IOException e) {
                throw CommonExceptionEnum.COMMON_ERROR.toException("读取请求体出错：" + e.getMessage(), e);
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        log.warn("读取请求体完成后关闭BufferedReader失败：{}", e.getMessage(), e);
                    }
                }
            }
        }

        @Override
        public ServletInputStream getInputStream() {
            // 判空
            if (this.body == null) {
                return null;
            }
            // 由String只能构造字节数组输入流
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                /**
                 * 这是在重写一个输入流对象的读取方法，需要保证每次都从 同一个 输入流进行读取
                 **/
                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
        }

    }
}