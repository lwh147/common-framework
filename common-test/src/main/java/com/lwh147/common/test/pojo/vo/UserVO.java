package com.lwh147.common.test.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户VO
 *
 * @author lwh
 * @date 2021/12/15 16:58
 **/
@Data
@EqualsAndHashCode
@ApiModel(description = "用户VO")
public class UserVO {
    @ApiModelProperty(value = "id", example = "1234567891234567899")
    protected Long id;
    @ApiModelProperty(value = "name", example = "李四")
    private String name;
    @ApiModelProperty(value = "简介", example = "个人资料")
    private String profile;
}
