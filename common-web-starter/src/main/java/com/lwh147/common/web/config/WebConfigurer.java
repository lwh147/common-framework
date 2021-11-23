package com.lwh147.common.web.config;

import com.lwh147.common.web.config.filter.RequestEncodingFilter;
import com.lwh147.common.web.exception.ExceptionResolver;
import com.lwh147.common.web.logger.RequestLoggerInterceptor;
import com.lwh147.common.web.logger.filter.RequestReplaceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Web相关配置，拦截器、过滤器、全局异常处理器等
 *
 * @author lwh
 * @date 2021/11/18 10:00
 **/
@Slf4j
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Resource
    private ExceptionResolver exceptionResolver;
    @Resource
    private RequestReplaceFilter requestReplaceFilter;
    @Resource
    private RequestEncodingFilter requestEncodingFilter;
    @Resource
    private RequestLoggerInterceptor requestLoggerInterceptor;

    /**
     * 配置全局异常处理器
     **/
    @Override
    public void configureHandlerExceptionResolvers(@Nonnull List<HandlerExceptionResolver> resolvers) {
        log.debug("配置并开启全局异常处理");
        resolvers.add(exceptionResolver);
    }

    /**
     * 配置统一编码过滤器
     **/
    @Bean
    public FilterRegistrationBean<RequestEncodingFilter> requestEncodingFilterRegister() {
        FilterRegistrationBean<RequestEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(requestEncodingFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("encodingFilter");
        registrationBean.setOrder(1);
        log.debug("配置并开启统一编码过滤器[UTF-8]");
        return registrationBean;
    }

    /**
     * 配置可重复读请求替换过滤器
     **/
    @Bean
    public FilterRegistrationBean<RequestReplaceFilter> requestReplaceFilterRegister() {
        FilterRegistrationBean<RequestReplaceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(requestReplaceFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("logFilter");
        registrationBean.setOrder(2);
        log.debug("配置并开启可重复读包装请求替换过滤器");
        return registrationBean;
    }

    /**
     * 配置请求日志记录拦截器
     **/
    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        // 拦截白名单
        List<String> whiteList = new ArrayList<>();
        whiteList.add("/");
        whiteList.add("/error");
        whiteList.add("/csrf");
        whiteList.add("/swagger-resources/**");
        whiteList.add("/static/**");
        whiteList.add("/webjars/**");
        whiteList.add("/favicon.ico");

        registry.addInterceptor(requestLoggerInterceptor)
                .excludePathPatterns(whiteList)
                .addPathPatterns("/**");
        log.debug("配置并开启日志记录拦截器，白名单：{}", Arrays.toString(whiteList.toArray()));
    }
}
