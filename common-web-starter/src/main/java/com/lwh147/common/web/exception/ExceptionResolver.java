package com.lwh147.common.web.exception;

import com.lwh147.common.core.exception.EnhancedRuntimeException;
import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.core.schema.response.RespBody;
import com.lwh147.common.util.JacksonUtils;
import com.lwh147.common.util.Strings;
import com.lwh147.common.util.constant.HttpConstant;
import com.lwh147.common.web.properties.WebProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * 全局异常处理器
 *
 * @author lwh
 * @date 2021/11/10 15:36
 **/
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    /**
     * 应用名称
     **/
    @Value("${spring.application.name:appName}")
    private String appName;
    /**
     * Web配置
     **/
    @Resource
    private WebProperties webProperties;
    /**
     * 单例的异常转换器池
     **/
    private ExceptionConverterPoolSingleton poolSingleton;

    /**
     * 在PostConstruct阶段完成异常转换器池的创建和初始化
     * <p>
     * 因为需要注入 {@link ExceptionResolver#webProperties} ，所以不
     * 能在声明时直接赋值 或 在构造器中初始化
     **/
    @PostConstruct
    private void postConstruct() {
        // 此时 webProperties 已完成注入
        poolSingleton = ExceptionConverterPoolSingleton.newInstance(webProperties.getGlobalExceptionHandler().getConverterScanCustomPackage());
    }

    @Override
    public ModelAndView resolveException(@Nullable HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse, Object o, @Nullable Exception e) {
        EnhancedRuntimeException ee;
        // 判断参数是否合法
        if (httpServletRequest == null || httpServletResponse == null || e == null) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("上下文环境异常: 请求或响应或异常对象为null");
            // 判断是不是增强异常类型
        } else if (e instanceof EnhancedRuntimeException) {
            // 是，强转
            ee = (EnhancedRuntimeException) e;
        } else {
            // 否，转换
            ee = poolSingleton.convert(e);
        }
        // 如果是当前服务发生异常，设置全局唯一异常追踪id，否则保持不变
        if (Strings.isBlank(ee.getTraceId())) {
            ee.setTraceId(UUID.randomUUID().toString());
        }
        // 如果是当前服务发生异常，设置服务名，否则保持不变
        if (Strings.isBlank(ee.getSource())) {
            ee.setSource(appName);
        }
        // 打印异常信息记录日志
        this.doLog(ee);
        // 写入响应体
        this.writeRespBody(httpServletResponse, ee);
        // 返回
        return new ModelAndView();
    }

    /**
     * 将失败响应写入响应体
     *
     * @param response 目标响应对象
     * @param ee       增强异常
     **/
    private void writeRespBody(HttpServletResponse response, EnhancedRuntimeException ee) {
        // 构造失败响应体
        RespBody<?> respBody = RespBody.failure(ee);
        // 设置响应头
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setHeader(HttpConstant.Header.CONTENT_TYPE, HttpConstant.ContentType.APPLICATION_JSON_CHARSET_UTF_8);
        // 写入响应内容
        try (PrintWriter pw = response.getWriter()) {
            pw.write(JacksonUtils.toJsonStr(respBody));
            pw.flush();
        } catch (IOException e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("写入响应体失败: " + e.getMessage(), e);
        }
    }

    /**
     * 记录日志并打印异常调用栈信息
     *
     * @param e 增强异常
     **/
    private void doLog(EnhancedRuntimeException e) {
        if (e.getCause() != null) {
            log.error(e.toString(), e.getCause());
        } else {
            log.error(e.toString(), e);
        }
    }
}