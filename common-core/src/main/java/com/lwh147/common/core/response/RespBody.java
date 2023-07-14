package com.lwh147.common.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lwh147.common.core.exception.ICommonException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 通用响应体结构封装
 * <p>
 * {@code @JsonInclude(JsonInclude.Include.NON_NULL)} 不为null时序列化，为null时忽略，基于Jackson
 *
 * @author lwh
 * @date 2021/10/22 10:45
 **/
@Data
@Builder
@EqualsAndHashCode
@ApiModel(description = "通用响应体结构封装")
public class RespBody<T> implements Serializable {
    /**
     * 成功与否
     **/
    @ApiModelProperty(value = "成功与否", example = "true", required = true)
    private Boolean success;
    /**
     * 业务响应数据
     **/
    @ApiModelProperty(value = "业务响应数据")
    private T data;
    /**
     * 错误来源
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误来源", example = "common-service")
    private String errorSource;
    /**
     * 错误码
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误码", example = "00000")
    private String errorCode;
    /**
     * 错误提示
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误描述", example = "枚举类型转换错误")
    private String errorMessage;
    /**
     * 错误详情
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误详情", example = "找不到枚举类型[CommonEnum.ERROR]")
    private String errorDetail;

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
        return RespBody.<T>builder().success(true)
                .data(data)
                .build();
    }

    /**
     * 构建失败响应
     *
     * @param ice 自定义异常行为规范接口对象
     * @return 失败响应
     **/
    public static RespBody<?> failure(ICommonException ice) {
        return builder().success(false)
                .errorSource(ice.getSource())
                .errorCode(ice.getCode())
                .errorMessage(ice.getDescription())
                .errorDetail(ice.getMessage())
                .build();
    }
}