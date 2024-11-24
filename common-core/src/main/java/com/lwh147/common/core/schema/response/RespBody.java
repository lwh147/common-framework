package com.lwh147.common.core.schema.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lwh147.common.core.exception.EnhancedRuntimeException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 通用响应体结构封装
 *
 * @author lwh
 * @date 2021/10/22 10:45
 **/
@Data
@Builder
@EqualsAndHashCode
@ApiModel(description = "通用响应体结构封装")
public class RespBody<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功与否
     **/
    @ApiModelProperty(value = "成功与否", required = true, example = "true")
    private Boolean success;
    /**
     * 业务响应数据
     **/
    @ApiModelProperty(value = "业务响应数据")
    private T data;
    /**
     * 全局唯一的异常追踪id
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "全局唯一的异常追踪id", example = "a727e1ca-ba8d-47ed-a882-e96f67e3a7f4")
    private String traceId;
    /**
     * 异常源头微服务名
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "异常源头微服务名", example = "common-service")
    private String source;
    /**
     * 异常枚举编码
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "异常枚举编码", example = "00000")
    private String code;
    /**
     * 异常枚举名称
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "异常枚举名称", example = "枚举类型转换错误")
    private String name;
    /**
     * 异常信息
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "异常信息", example = "找不到枚举类型[CommonEnum.ERROR]")
    private String message;
    /**
     * 异常代码行
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "异常代码行", example = "c.l.c.test.controller.UserController.test(UserController.java:62)")
    private String at;

    /**
     * 构建无响应数据的成功响应
     *
     * @return 无响应数据的成功响应
     **/
    public static RespBody<?> success() {
        return builder().success(true).build();
    }

    /**
     * 构建含有响应数据的成功响应
     *
     * @param data 业务响应数据
     * @return 有响应数据的成功响应
     **/
    public static <T> RespBody<T> success(T data) {
        return RespBody.<T>builder().success(true).data(data).build();
    }

    /**
     * 构建失败响应
     *
     * @param ice 自定义异常行为规范接口对象
     * @return 失败响应
     **/
    public static RespBody<?> failure(EnhancedRuntimeException ice) {
        return builder().success(false).traceId(ice.getTraceId()).source(ice.getSource())
                .code(ice.getCode()).name(ice.getName())
                .message(ice.getMessage()).at(ice.getAt()).build();
    }
}