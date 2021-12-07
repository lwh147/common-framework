package com.lwh147.common.web.autoconfigure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lwh147.common.web.autoconfigure.filter.RequestEncodingFilter;
import com.lwh147.common.web.exception.ExceptionResolver;
import com.lwh147.common.web.logger.RequestLoggerInterceptor;
import com.lwh147.common.web.logger.filter.RequestReplaceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * Web相关配置，拦截器、过滤器、全局异常处理器等
 *
 * @author lwh
 * @date 2021/11/18 10:00
 **/
@Slf4j
@Configuration
public class WebAutoConfiguration implements WebMvcConfigurer {
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String dateFormat;
    @Value("${spring.jackson.time-zone:GMT+8}")
    private String timeZone;
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
        whiteList.add("/swagger-ui/**");
        whiteList.add("/static/**");
        whiteList.add("/webjars/**");
        whiteList.add("/favicon.ico");

        registry.addInterceptor(requestLoggerInterceptor)
                .excludePathPatterns(whiteList)
                .addPathPatterns("/**");
        log.debug("配置并开启日志记录拦截器，白名单：{}", Arrays.toString(whiteList.toArray()));
    }

    /**
     * 配置Jackson的全局序列化策略：
     * <p>
     * 1.将 {@link Long} 转换成String
     * <p>
     * 2.将 {@link java.math.BigDecimal} 转换成PlainString，不采用科学计数法，完整打印数值
     * <p>
     * 3.设置默认时区为：GMT+8，默认日期时间格式为：yyyy-MM-dd HH:mm:ss
     * <p>
     * 4.json与java对象属性不全对应时也进行反序列化，不报错
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 新建转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 添加并设置转换策略
        ObjectMapper objectMapper = converter.getObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        // 将Long转换成String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);
        // 将BigDecimal转换成PlainString，不采用科学计数法，完整打印数值
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        // JSON与Java对象属性不全对应时也进行反序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 日期时间格式
        objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
        objectMapper.setTimeZone(TimeZone.getTimeZone(timeZone));

        objectMapper.registerModule(simpleModule);
        converter.setObjectMapper(objectMapper);
        // 添加转换器
        converters.add(0, converter);
    }
}
