package com.lwh147.common.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lwh147.common.mybatisplus.model.DataModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author lwh
 * @date 2021/12/6 17:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户实体")
@TableName("user")
public class User extends DataModel<User> implements Serializable {
    @ApiModelProperty(value = "name", example = "李四")
    private String name;
    @ApiModelProperty(value = "sex", example = "男")
    private String sex;
    @ApiModelProperty(value = "age", example = "34")
    private Integer age;
    @ApiModelProperty(value = "简介", example = "个人资料")
    private String profile;
}
