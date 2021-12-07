package com.lwh147.common.swagger.autoconfigure;

import com.lwh147.common.swagger.properties.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Swagger配置类
 * <p>
 * 前提条件：
 * <p>
 * 上下文中不存在Swagger配置Bean
 * <p>
 * springfox.documentation.enabled 配置项缺省或配置为 {@code true}
 *
 * @author lwh
 * @date 2021/11/19 16:55
 **/
@Slf4j
@Configuration
@ConditionalOnMissingBean(Docket.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfiguration {
    @Resource
    private SwaggerProperties swaggerProperties;

    /**
     * 默认配置Bean生成方法
     **/
    @Bean
    public Docket createRestApi() {
        // 构建Swagger配置
        ApiSelectorBuilder builder = new Docket(DocumentationType.OAS_30)
                .apiInfo(this.apiInfo())
                .select();
        // 默认扫描所有包
        builder.apis(RequestHandlerSelectors.any());
        // 默认只排除路径/error
        builder.paths(PathSelectors.regex(SwaggerProperties.DEFAULT_EXCLUDED_PATH).negate());
        // 配置指定的扫描基础包
        if (Objects.nonNull(swaggerProperties.getPackages())) {
            for (String pkg : swaggerProperties.getPackages()) {
                builder.apis(RequestHandlerSelectors.basePackage(pkg));
            }
        }
        // 配置指定的路径匹配规则
        if (Objects.nonNull(swaggerProperties.getPaths())) {
            for (String reg : swaggerProperties.getPaths()) {
                builder.paths(PathSelectors.regex(reg));
            }
        }
        // 配置指定排除的路径
        if (Objects.nonNull(swaggerProperties.getExcludedPaths())) {
            for (String reg : swaggerProperties.getExcludedPaths()) {
                builder.paths(PathSelectors.regex(reg).negate());
            }
        }
        log.debug("配置并开启Swagger[{}]", swaggerProperties);
        return builder.build();
    }

    /**
     * 构建API文档简介和联系人信息
     *
     * @return API信息
     **/
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(swaggerProperties.getContact().toSwaggerContact())
                .build();
    }
}
