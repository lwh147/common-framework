package com.lwh147.common.swagger.autoconfigure;

import com.lwh147.common.core.enums.ValueNameEnum;
import com.lwh147.common.swagger.properties.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

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
@EnableSwagger2
@ConditionalOnMissingBean(Docket.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfiguration implements ModelPropertyBuilderPlugin {
    @Resource
    private SwaggerProperties swaggerProperties;

    /**
     * 默认配置Bean生成方法
     **/
    @Bean
    public Docket createRestApi() {
        // 构建Swagger配置
        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select();
        // 默认扫描所有包
        builder.apis(RequestHandlerSelectors.any());
        // 默认只排除路径/error
        builder.paths(input -> !input.matches(SwaggerProperties.DEFAULT_EXCLUDED_PATH));
        // 配置指定的扫描基础包
        if (!CollectionUtils.isEmpty(swaggerProperties.getPackages())) {
            for (String pkg : swaggerProperties.getPackages()) {
                builder.apis(RequestHandlerSelectors.basePackage(pkg));
            }
        }
        // 配置指定的路径匹配规则
        if (!CollectionUtils.isEmpty(swaggerProperties.getPaths())) {
            for (String reg : swaggerProperties.getPaths()) {
                builder.paths(PathSelectors.regex(reg));
            }
        }
        // 配置指定排除的路径
        if (!CollectionUtils.isEmpty(swaggerProperties.getExcludedPaths())) {
            for (String reg : swaggerProperties.getExcludedPaths()) {
                builder.paths(input -> !input.matches(reg));
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

    @Override
    public void apply(ModelPropertyContext modelPropertyContext) {
        if (swaggerProperties.getEnabled() == null || !swaggerProperties.getEnabled()) {
            return;
        }

        //获取当前字段的类型
        final Class<?> fieldType = modelPropertyContext.getBeanPropertyDefinition().isPresent() ?
                modelPropertyContext.getBeanPropertyDefinition().get().getField().getRawType() : null;

        //为枚举字段设置注释
        this.valueNameEnumDescHandler(modelPropertyContext, fieldType);
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return false;
    }

    /**
     * Swagger参数字段类型是 {@link ValueNameEnum} 实现枚举类字段时，格式化输出枚举可取值列表
     **/
    private void valueNameEnumDescHandler(ModelPropertyContext context, Class<?> fieldType) {
        // TODO
        // Optional<ApiModelProperty> annotation = Optional.absent();
        //
        // // 找到 @ApiModelProperty 注解修饰的枚举类
        // if (context.getAnnotatedElement().isPresent()) {
        //     annotation = annotation
        //             .or(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        // }
        // if (context.getBeanPropertyDefinition().isPresent()) {
        //     annotation = annotation.or(Annotations.findPropertyAnnotation(
        //             context.getBeanPropertyDefinition().get(),
        //             ApiModelProperty.class));
        // }
        //
        // //没有@ApiModelProperty 或者 notes 属性没有值，直接返回
        // if (!annotation.isPresent()) {
        //     return;
        // }
        //
        // //@ApiModelProperties中的notes指定的class类型
        // Class rawPrimaryType;
        // try {
        //     rawPrimaryType = Class.forName((annotation.get()).notes());
        // } catch (ClassNotFoundException e) {
        //     //如果指定的类型无法转化，直接忽略
        //     return;
        // }
        //
        // Object[] subItemRecords = null;
        // SwaggerDisplayEnum swaggerDisplayEnum = AnnotationUtils
        //         .findAnnotation(rawPrimaryType, SwaggerDisplayEnum.class);
        // // 判断是否存在 @SwaggerDisplayEnum 注解，并且 rawPrimaryType 是枚举
        // if (null != swaggerDisplayEnum && Enum.class.isAssignableFrom(rawPrimaryType)) {
        //     // 拿到枚举的所有的值
        //     subItemRecords = rawPrimaryType.getEnumConstants();
        // }
        // if (null == subItemRecords) {
        //     return;
        // }
        //
        // final List<String> displayValues =
        //         Arrays.stream(subItemRecords)
        //                 .filter(Objects::nonNull)
        //                 // 调用枚举类的 toString 方法
        //                 .map(Object::toString)
        //                 .filter(Objects::nonNull)
        //                 .collect(Collectors.toList());
        //
        // String joinText = " (" + String.join("; ", displayValues) + ")";
        // try {
        //     // 拿到字段上原先的描述
        //     Field mField = ModelPropertyBuilder.class.getDeclaredField("description");
        //     mField.setAccessible(true);
        //     // context 中的 builder 对象保存了字段的信息
        //     joinText = mField.get(context.getBuilder()) + joinText;
        // } catch (Exception e) {
        //     log.error(e.getMessage());
        // }
        //
        // // 设置新的字段说明并且设置字段类型
        // final ResolvedType resolvedType = context.getResolver().resolve(fieldType);
        // context.getBuilder().description(joinText).type(resolvedType);
    }
}