package com.lwh147.common.web.autoconfigure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lwh147.common.core.enums.DbColumnEnum;
import com.lwh147.common.core.enums.ValueNameEnum;
import com.lwh147.common.core.enums.serializer.EnumDeserializer;
import com.lwh147.common.core.enums.serializer.EnumSerializer;
import com.lwh147.common.util.constant.DateTimeConstant;
import com.lwh147.common.web.component.BannerPrinter;
import com.lwh147.common.web.component.DbColumnEnumConverter;
import com.lwh147.common.web.component.ValueNameEnumConverter;
import com.lwh147.common.web.exception.ExceptionResolver;
import com.lwh147.common.web.filter.RepeatableReadRequestFilter;
import com.lwh147.common.web.filter.RequestEncodingFilter;
import com.lwh147.common.web.interceptor.RequestLoggingInterceptor;
import com.lwh147.common.web.properties.WebProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.math.BigDecimal;
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
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration implements WebMvcConfigurer {
    @Value("${spring.jackson.date-format:}")
    private final String dateFormat = DateTimeConstant.DEFAULT_DATETIME_PATTERN;
    @Value("${spring.jackson.time-zone:}")
    private final String timeZone = DateTimeConstant.DEFAULT_TIMEZONE;
    @Resource
    private WebProperties webProperties;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ExceptionResolver exceptionResolver;
    @Resource
    private RepeatableReadRequestFilter repeatableReadRequestFilter;
    @Resource
    private RequestEncodingFilter requestEncodingFilter;
    @Resource
    private RequestLoggingInterceptor requestLoggingInterceptor;

    /**
     * 优先注入BannerPrinter完成Banner的打印
     *
     * @param bannerPrinter 非必须，如果不存在则说明配置为不打印
     **/
    public WebAutoConfiguration(@Autowired(required = false) BannerPrinter bannerPrinter) {
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
    public FilterRegistrationBean<RepeatableReadRequestFilter> requestReplaceFilterRegister() {
        FilterRegistrationBean<RepeatableReadRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(repeatableReadRequestFilter);
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

        registry.addInterceptor(requestLoggingInterceptor)
                .excludePathPatterns(whiteList)
                .addPathPatterns("/**");
        log.debug("配置并开启日志记录拦截器，白名单{}", Arrays.toString(whiteList.toArray()));
    }

    /**
     * 配置全局异常处理器
     **/
    @Override
    public void extendHandlerExceptionResolvers(@Nonnull List<HandlerExceptionResolver> resolvers) {
        if (webProperties.getGlobalExceptionHandler().getEnabled()) {
            log.debug("配置并开启全局异常处理器");
            resolvers.add(0, exceptionResolver);
        }
    }

    /**
     * 自定义ObjectMapper替换SpringBoot使用的默认ObjectMapper
     * <p>
     * 1.将 {@link Number} 转换成String
     * <p>
     * 2.将 {@link java.math.BigDecimal} 转换成Plain格式不采用科学计数法，完整打印数值
     * <p>
     * 3.设置默认时区为：GMT+8，默认日期时间格式为：yyyy-MM-dd HH:mm:ss
     * <p>
     * 4.json与java对象属性不全对应时也进行反序列化
     * <p>
     * 5.自定义实现了 {@link DbColumnEnum} 或 {@link ValueNameEnum} 接口的枚举类型的（反）序列化策略
     *
     * @apiNote 空值不转换 @JsonInclude(JsonInclude.Include.NON_NULL)
     * <p>
     * 日期时间格式化 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
     * <p>
     * 序列化为String或配置自定义序列化策略 @JsonSerialize(using = ToStringSerializer.class)
     **/
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper webObjectMapper = JsonMapper.builder()
                // SpringBoot默认行为
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                // JSON与Java对象属性不全对应时也进行反序列化
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 将数字序列化为字符串
                // .configure(JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS, true)
                // 将BigDecimal转换成PlainString，不采用科学计数法，完整打印数值
                .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
                .build();

        // 日期时间格式
        webObjectMapper.setTimeZone(TimeZone.getTimeZone(timeZone));
        webObjectMapper.setDateFormat(new SimpleDateFormat(dateFormat));

        SimpleModule simpleModule = new SimpleModule();

        // 配置实现了自定义接口的枚举类型的（反）序列化策略，以下两种方式选其一即可
        simpleModule.setSerializers(new EnumSerializer());
        simpleModule.setDeserializers(new EnumDeserializer());
        // simpleModule.setSerializerModifier(new EnumSerializerModifier());
        // simpleModule.setDeserializerModifier(new EnumDeserializerModifier());

        // Long和BigDecimal转换为字符串输出
        ToStringSerializer toStringSerializer = new ToStringSerializer();
        simpleModule.addSerializer(Long.class, toStringSerializer);
        simpleModule.addSerializer(long.class, toStringSerializer);
        simpleModule.addSerializer(BigDecimal.class, toStringSerializer);

        webObjectMapper.registerModule(simpleModule);

        return webObjectMapper;
    }

    /**
     * 配置Jackson的全局序列化策略
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 新建转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        // 添加转换器
        converters.add(0, converter);
    }

    /**
     * 添加自定义枚举类型 {@link DbColumnEnum} 和 {@link ValueNameEnum} 的参数绑定转换策略
     **/
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ValueNameEnumConverter<>());
        registry.addConverter(new DbColumnEnumConverter<>());
    }
}