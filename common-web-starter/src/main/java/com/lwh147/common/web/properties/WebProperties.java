package com.lwh147.common.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Web配置类
 *
 * @author lwh
 * @date 2021/12/17 11:38
 **/
@Data
@ConfigurationProperties(prefix = "web")
public class WebProperties {
    /**
     * 是否打印框架Banner
     **/
    private Boolean enableBannerPrint = true;
    /**
     * 全局异常处理配置
     **/
    @NestedConfigurationProperty
    private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    /**
     * 全局异常处理配置类
     **/
    @Data
    public static class GlobalExceptionHandler {
        /**
         * 是否开启全局异常处理
         **/
        private Boolean enabled = true;
        /**
         * 自定义的异常转换器扫描包路径
         **/
        private String converterScanCustomPackage;
    }
}
