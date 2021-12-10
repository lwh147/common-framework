package com.lwh147.common.swagger.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * Swagger配置类
 *
 * @author lwh
 * @date 2021/11/22 17:06
 **/
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * 是否开启Swagger，共用Springfox的配置项，缺省默认开启
     **/
    private Boolean enabled = true;
    /**
     * 扫描控制器的包路径，可配置多个，默认为空即扫描所有包
     **/
    private List<String> packages;
    /**
     * 匹配的控制器映射路径，可配置多个，默认为空即匹配所有路径
     **/
    private List<String> paths;
    /**
     * 要排除的控制器映射路径，可配置多个，默认为空
     **/
    private List<String> excludedPaths;
    /**
     * API版本号，默认：1.0.0
     **/
    private String version = "1.0.0";
    /**
     * 页面标题，默认为配置中的应用名
     **/
    @Value("${spring.application.name:app-name}")
    private String title;
    /**
     * 文档描述，默认为：接口文档
     **/
    private String description = "接口文档";
    /**
     * 联系人信息
     **/
    @NestedConfigurationProperty
    private Contact contact = new Contact();

    @Data
    public static class Contact {
        /**
         * 联系人姓名，默认为：管理员
         **/
        private String name = "管理员";
        /**
         * 联系人网页地址，默认为百度
         **/
        private String url = "https://www.baidu.com";
        /**
         * 联系人邮箱，默认为空
         **/
        private String email;

        /**
         * 将自定义Contact转换为Swagger配置中的Contact对象
         **/
        public springfox.documentation.service.Contact toSwaggerContact() {
            return new springfox.documentation.service.Contact(this.name, this.url, this.email);
        }
    }

    /**
     * 默认排除的扫描路径
     **/
    public static final String DEFAULT_EXCLUDED_PATH = "^/error$";
}
