package com.lwh147.common.test.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 用户更新DTO
 *
 * @author lwh
 * @date 2021/12/15 17:08
 **/
@Data
@EqualsAndHashCode
@ApiModel(description = "用户更新DTO")
public class UserUpdateDTO {
    @ApiModelProperty(value = "id", required = true, example = "1234567891234567899")
    protected Long id;
    @ApiModelProperty(value = "name", example = "李四")
    private String name;
    @ApiModelProperty(value = "sex", example = "男")
    private String sex;
    @ApiModelProperty(value = "age", example = "34")
    @Min(value = 0L, message = "年龄介于0-200之间")
    @Max(value = 200L, message = "年龄介于0-200之间")
    private Integer age;
    @ApiModelProperty(value = "简介", example = "个人资料")
    private String profile;
}
