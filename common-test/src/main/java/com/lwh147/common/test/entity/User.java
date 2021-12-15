package com.lwh147.common.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwh147.common.core.constant.DateTimeConstant;
import com.lwh147.common.mybatisplus.model.DataModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    @ApiModelProperty(value = "name", required = true, example = "李四")
    private String name;
    @ApiModelProperty(value = "sex", required = true, example = "男")
    private String sex;
    @ApiModelProperty(value = "age", required = true, example = "34")
    private Integer age;
    @ApiModelProperty(value = "简介", required = true, example = "个人资料")
    private String profile;
    @ApiModelProperty(value = "创建时间", required = true, example = "2021-12-09 16:13:00")
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_FORMAT)
    private Date createTime;
    @ApiModelProperty(value = "更新时间", required = true, example = "2021-12-09 16:13:00")
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_FORMAT)
    private Date updateTime;
}
