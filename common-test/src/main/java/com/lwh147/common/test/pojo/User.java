package com.lwh147.common.test.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwh147.common.core.constant.DateTimeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode
public class User {
    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private String profile;
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_FORMAT)
    protected Date createTime;
}
