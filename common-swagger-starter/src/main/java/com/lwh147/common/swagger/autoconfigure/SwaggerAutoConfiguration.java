package com.lwh147.common.swagger.autoconfigure;

import com.lwh147.common.core.enums.DbColumnEnum;
import com.lwh147.common.core.enums.ValueNameEnum;
import com.lwh147.common.swagger.properties.SwaggerProperties;
import io.swagger.annotations.ApiModelProperty;
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
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Swagger配置类
 * <p>
 * 为了增强文档对枚举类型的支持，该配置类针对实现了 {@link DbColumnEnum} 或 {@link ValueNameEnum} 接口的枚举类型参数，在文档
 * 生成测策略上进行扩展，具体分为三种情况：
 * <p>
 * {@link ModelPropertyBuilderPlugin} 作为 JavaBean 属性且打了{@link ApiModelProperty} 注解接受请求体参数时 (接口参数前使用
 * {@code @RequestBody} 注解）
 * <p>
 * {@link ParameterBuilderPlugin} 直接作为打了{@code @RequestParam} 注解的请求参数时
 * <p>
 * {@link ExpandedParameterBuilderPlugin} 作为 JavaBean 属性接受请求参数时（未打{@code @RequestParam} 注解）
 * <p>
 * 前提条件：
 * <p>
 * 上下文中不存在Swagger配置Bean
 * <p>
 * springfox.documentation.enabled 配置项缺省或配置为 {@code true}
 * <p>
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
public class SwaggerAutoConfiguration implements ModelPropertyBuilderPlugin, ParameterBuilderPlugin,
        ExpandedParameterBuilderPlugin {
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
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(swaggerProperties.getContact().toSwaggerContact())
                .build();
    }

    /**
     * 作为 JavaBean 属性且打了{@link ApiModelProperty} 注解接受请求体参数时 (接口参数前使用 {@code @RequestBody} 注解）
     **/
    @Override
    public void apply(ModelPropertyContext context) {
        if (swaggerProperties.getEnabled() == null || !swaggerProperties.getEnabled()) {
            return;
        }
        if (!context.getBeanPropertyDefinition().isPresent()) {
            return;
        }

        final Class<?> type = context.getBeanPropertyDefinition().get().getRawPrimaryType();

        // 如果是目标枚举类型则修改 description 和 allowableValues 输出格式
        if (type.isEnum()) {
            Set<Class<?>> interfaceSet = Arrays.stream(type.getInterfaces()).collect(Collectors.toSet());
            if (interfaceSet.contains(ValueNameEnum.class)) {
                StringBuilder description = new StringBuilder();
                List<String> allowableListValues = new ArrayList<>();
                Arrays.stream(type.getEnumConstants()).map(obj -> (ValueNameEnum<? extends Serializable>) obj)
                        .forEach(valueNameEnum -> {
                            allowableListValues.add(valueNameEnum.getValue().toString());
                            description.append(valueNameEnum.getValue().toString())
                                    .append("-")
                                    .append(valueNameEnum.getName())
                                    .append("，");
                        });
                context.getBuilder().description(description.substring(0, description.length() - 1))
                        .allowableValues(new AllowableListValues(allowableListValues, "LIST"));
            } else if (interfaceSet.contains(DbColumnEnum.class)) {
                StringBuilder description = new StringBuilder();
                List<String> allowableListValues = new ArrayList<>();
                Arrays.stream(type.getEnumConstants()).map(obj -> (DbColumnEnum) obj).forEach(dbColumnEnum -> {
                    allowableListValues.add(dbColumnEnum.getParamName());
                    description.append(dbColumnEnum.getParamName())
                            .append("-")
                            .append(dbColumnEnum.getName())
                            .append("，");
                });
                context.getBuilder().description(description.substring(0, description.length() - 1))
                        .allowableValues(new AllowableListValues(allowableListValues, "LIST"))
                        .example((Object) allowableListValues.get(0));
            }
        }
    }

    /**
     * 直接作为打了{@code @RequestParam} 注解的请求参数时
     **/
    @Override
    public void apply(ParameterContext context) {
        if (swaggerProperties.getEnabled() == null || !swaggerProperties.getEnabled()) {
            return;
        }
        Parameter parameter = context.parameterBuilder().build();
        if (!parameter.getType().isPresent()) {
            return;
        }

        final Class<?> type = parameter.getType().get().getErasedType();

        // 如果是目标枚举类型则修改 description 和 allowableValues 输出格式
        if (type.isEnum()) {
            Set<Class<?>> interfaceSet = Arrays.stream(type.getInterfaces()).collect(Collectors.toSet());
            if (interfaceSet.contains(ValueNameEnum.class)) {
                StringBuilder description = new StringBuilder();
                List<String> allowableListValues = new ArrayList<>();
                Arrays.stream(type.getEnumConstants()).map(obj -> (ValueNameEnum<? extends Serializable>) obj)
                        .forEach(valueNameEnum -> {
                            allowableListValues.add(valueNameEnum.getValue().toString());
                            description.append(valueNameEnum.getValue().toString())
                                    .append("-")
                                    .append(valueNameEnum.getName())
                                    .append("，");
                        });
                context.parameterBuilder().description(description.substring(0, description.length() - 1))
                        .allowableValues(new AllowableListValues(allowableListValues, "LIST"));
            } else if (interfaceSet.contains(DbColumnEnum.class)) {
                StringBuilder description = new StringBuilder();
                List<String> allowableListValues = new ArrayList<>();
                Arrays.stream(type.getEnumConstants()).map(obj -> (DbColumnEnum) obj).forEach(dbColumnEnum -> {
                    allowableListValues.add(dbColumnEnum.getParamName());
                    description.append(dbColumnEnum.getParamName())
                            .append("-")
                            .append(dbColumnEnum.getName())
                            .append("，");
                });
                context.parameterBuilder().description(description.substring(0, description.length() - 1))
                        .allowableValues(new AllowableListValues(allowableListValues, "LIST"));
            }
        }
    }

    /**
     * 作为 JavaBean 属性接受请求参数时（未打{@code @RequestParam} 注解）
     **/
    @Override
    public void apply(ParameterExpansionContext context) {
        if (swaggerProperties.getEnabled() == null || !swaggerProperties.getEnabled()) {
            return;
        }
        Parameter parameter = context.getParameterBuilder().build();
        if (!parameter.getType().isPresent()) {
            return;
        }

        Class<?> type = parameter.getType().get().getErasedType();

        // 如果是目标枚举类型则修改 description 和 allowableValues 输出格式
        if (type.isEnum()) {
            Set<Class<?>> interfaceSet = Arrays.stream(type.getInterfaces()).collect(Collectors.toSet());
            if (interfaceSet.contains(ValueNameEnum.class)) {
                StringBuilder description = new StringBuilder();
                List<String> allowableListValues = new ArrayList<>();
                Arrays.stream(type.getEnumConstants()).map(obj -> (ValueNameEnum<? extends Serializable>) obj)
                        .forEach(valueNameEnum -> {
                            allowableListValues.add(valueNameEnum.getValue().toString());
                            description.append(valueNameEnum.getValue().toString())
                                    .append("-")
                                    .append(valueNameEnum.getName())
                                    .append("，");
                        });
                context.getParameterBuilder().description(description.substring(0, description.length() - 1))
                        .allowableValues(new AllowableListValues(allowableListValues, "LIST"));
            } else if (interfaceSet.contains(DbColumnEnum.class)) {
                StringBuilder description = new StringBuilder();
                List<String> allowableListValues = new ArrayList<>();
                Arrays.stream(type.getEnumConstants()).map(obj -> (DbColumnEnum) obj).forEach(dbColumnEnum -> {
                    allowableListValues.add(dbColumnEnum.getParamName());
                    description.append(dbColumnEnum.getParamName())
                            .append("-")
                            .append(dbColumnEnum.getName())
                            .append("，");
                });
                context.getParameterBuilder().description(description.substring(0, description.length() - 1))
                        .allowableValues(new AllowableListValues(allowableListValues, "LIST"));
            }
        }
    }

    /**
     * 是否是可处理的文档类型，默认返回支持
     **/
    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}