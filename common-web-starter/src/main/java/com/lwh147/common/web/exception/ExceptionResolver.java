package com.lwh147.common.web.exception;

import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.core.exception.ICommonException;
import com.lwh147.common.core.response.RespBody;
import com.lwh147.common.model.constant.HttpConstant;
import com.lwh147.common.util.JacksonUtils;
import com.lwh147.common.web.exception.converter.ExceptionConverterPoolSingleton;
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
        ICommonException ice;
        // 判断参数是否合法
        if (httpServletRequest == null || httpServletResponse == null || e == null) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("上下文环境异常: 请求或响应或异常对象为null");
        }
        // 判断是不是自定义异常
        else if (e instanceof ICommonException) {
            // 是，强转
            ice = (ICommonException) e;
        } else {
            // 否，转换
            ice = poolSingleton.convert(e);
        }
        // 微服务系统中，判断是否是下游错误的封装异常
        if (ice.getSource() == null) {
            ice.setSource(appName);
        }
        // 打印异常信息记录日志
        this.doLog(ice);
        // 写入响应体
        this.writeRespBody(httpServletResponse, ice);
        // 返回
        return new ModelAndView();
    }

    /**
     * 将失败响应写入响应体
     *
     * @param response 目标响应对象
     * @param ice      封装后的自定义异常
     **/
    private void writeRespBody(HttpServletResponse response, ICommonException ice) {
        // 构造失败响应体
        RespBody<?> respBody = RespBody.failure(ice);
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
     * @param ice 自定义异常
     **/
    private void doLog(ICommonException ice) {
        // 打印异常类记录信息，cause为null默认不打印任何信息，不会报错
        log.error(ice.toString(), ice.getCause());
    }
}