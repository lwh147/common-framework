package c.l.c.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lwh147.common.mybatisplus.schema.DataModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@ToString(callSuper = true)
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