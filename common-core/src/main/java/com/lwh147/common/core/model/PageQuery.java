package com.lwh147.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lwh147.common.core.constant.CommonConstant;
import com.lwh147.common.core.enums.ICommonEnum;
import com.lwh147.common.core.enums.OrderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * 通用分页请求结构
 *
 * @param <T> 排序字段名称<strong>枚举对象</strong>，且必须实现
 *            {@code com.lwh147.common.core.enums.ICommonEnum} 接口
 *            使用枚举是为了限制用户输入，<strong>防止SQL注入</strong>
 * @author lwh
 * @date 2021/10/22 10:44
 **/
@Data
@EqualsAndHashCode
@ApiModel(description = "通用分页请求结构")
public class PageQuery<T extends Enum<T> & ICommonEnum> implements Serializable {
    /**
     * 页码
     **/
    @Min(value = 1L, message = "页码必须为正整数")
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码，>=1", required = true, example = "1")
    private Long current;

    /**
     * 页大小
     **/
    @Max(value = 100L, message = "页大小必须为1-100（含）的整数")
    @Min(value = 1L, message = "页大小必须为1-100（含）的整数")
    @NotNull(message = "页大小不能为空")
    @ApiModelProperty(value = "页大小，1-100（含）", required = true, example = "10")
    private Long size;

    /**
     * 排序字段名
     * <p>
     * 驼峰格式
     * <p>
     * 枚举类型
     **/
    @ApiModelProperty(value = "需要排序的字段名称，驼峰格式", example = "createTime")
    private T columnName;

    /**
     * 排序类型
     **/
    @ApiModelProperty(value = "排序类型，升序：ASC，降序：DESC", example = "DESC")
    private OrderEnum order;

    /**
     * 排序sql后缀
     * <p>
     * {@code @JsonIgnore} Json序列化和反序列化时忽略该注解所注字段
     * {@code @ApiModelProperty(hidden = true)} 不在Swagger界面上展示
     **/
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String sortSqlSuffix;

    /**
     * 重写 {@code sortSqlSuffix} 属性的get方法
     * <p>
     * 用空格拼接 {@code columnName} 和 {@code order} 字段，
     * 两者任一为空时返回按创建时间降序排序的sql后缀
     **/
    public String getSortSqlSuffix() {
        // 默认按创建时间降序排序
        if (Objects.isNull(this.columnName) || Objects.isNull(this.order)) {
            return "create_time DESC";
        }
        // 驼峰转下划线
        String column = this.columnName.getValue().replaceAll("[A-Z]", "_$0")
                .toLowerCase(Locale.ROOT);
        // 使用空格拼接
        return column + CommonConstant.SPACE + this.order;
    }
}
