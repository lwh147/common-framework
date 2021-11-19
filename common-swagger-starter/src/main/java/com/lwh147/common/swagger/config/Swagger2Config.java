package com.lwh147.common.swagger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 *
 * @author lwh
 * @date 2021/11/19 16:55
 **/
@Slf4j
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket clientApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("client")
                .apiInfo(clientApiInfo())
                .select()
                .paths(PathSelectors.regex("/.*/.*"))
                .build();
    }

    public ApiInfo clientApiInfo() {
        return new ApiInfoBuilder()
                .title("common-service-api")
                .description("通用服务的客户端API文档")
                .version("1.0.0")
                .contact(new Contact("lwh147", "https://github.com/lwh147", "1479351399@qq.com"))
                .build();
    }
}
