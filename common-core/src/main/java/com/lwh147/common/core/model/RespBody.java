package com.lwh147.common.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lwh147.common.core.exception.ICommonException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 通用响应体结构
 * <p>
 * {@code @JsonInclude(JsonInclude.Include.NON_NULL)} 不为null时序列化，为null时忽略
 *
 * @author lwh
 * @date 2021/10/22 10:45
 **/
@Data
@Builder
@EqualsAndHashCode
@ApiModel(description = "通用响应结构")
public class RespBody<T> implements Serializable {
    /**
     * 成功与否
     **/
    @ApiModelProperty(value = "成功与否", example = "true", required = true)
    private Boolean success;
    /**
     * 业务响应数据
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "业务响应数据")
    private T data;
    /**
     * 错误码
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误码", example = "C000001")
    private String errorCode;
    /**
     * 提示信息
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误提示信息", example = "枚举类型转换错误")
    private String errorMessage;
    /**
     * 错误来源
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误来源", example = "common-service")
    private String errorSource;
    /**
     * 错误原因
     **/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "错误原因", example = "找不到枚举类型【CommonEnum.ERROR】")
    private String errorCausation;

    /**
     * 构建无响应数据的成功响应
     *
     * @return RespBody<?>
     **/
    public static RespBody<?> success() {
        return builder()
                .success(true)
                .build();
    }

    /**
     * 构建含有响应数据的成功响应
     *
     * @param data 业务响应数据
     * @return RespBody<T>
     **/
    public static <T> RespBody<T> success(T data) {
        // 为何这样写？参考PageResp的fromPage方法
        RespBodyBuilder<T> builder = builder();
        return builder
                .success(true)
                .data(data)
                .build();
    }

    /**
     * 构建失败响应
     *
     * @param ice 导致失败的异常
     * @return RespBody<?>
     **/
    public static RespBody<?> failure(ICommonException ice) {
        return builder()
                .success(false)
                .errorCode(ice.getCode())
                .errorMessage(ice.getMessage())
                .errorSource(ice.getSource())
                .errorCausation(ice.getCausation())
                .build();
    }
}
