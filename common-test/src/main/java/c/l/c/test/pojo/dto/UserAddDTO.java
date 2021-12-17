package c.l.c.test.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户添加DTO
 *
 * @author lwh
 * @date 2021/12/15 16:58
 **/
@Data
@EqualsAndHashCode
@ApiModel(description = "用户添加DTO")
public class UserAddDTO {
    @ApiModelProperty(value = "name", required = true, example = "李四")
    @NotBlank(message = "用户姓名不能为空")
    private String name;
    @ApiModelProperty(value = "sex", required = true, example = "男")
    @NotBlank(message = "用户性别不能为空")
    private String sex;
    @ApiModelProperty(value = "age", required = true, example = "34")
    @Min(value = 0L, message = "年龄介于0-200之间")
    @Max(value = 200L, message = "年龄介于0-200之间")
    @NotNull(message = "用户年龄不能为空")
    private Integer age;
    @ApiModelProperty(value = "简介", required = true, example = "个人资料")
    @NotBlank(message = "用户简介不能为空")
    private String profile;
}
