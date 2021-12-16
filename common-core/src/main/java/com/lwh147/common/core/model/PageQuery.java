package com.lwh147.common.core.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lwh147.common.core.enums.DefaultSrotOrderEnum;
import com.lwh147.common.core.enums.column.DefaultSortColumnEnum;
import com.lwh147.common.core.enums.column.IColumnEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * 通用分页请求结构
 *
 * @param <T> 排序字段名称<strong>枚举对象</strong>，必须实现
 *            {@link IColumnEnum} 接口
 *            <p>
 *            使用枚举是为了限制用户输入，<strong>防止SQL注入</strong>
 *            <p>
 *            详细参考 {@link DefaultSortColumnEnum}
 * @author lwh
 * @apiNote 必须作为<strong>请求体</strong>接受参数，否则数据绑定时
 * 枚举类型不能被识别，报 {@link IllegalArgumentException}
 * 错误
 * @date 2021/10/22 10:44
 **/
@Data
@EqualsAndHashCode
@ApiModel(description = "通用分页请求结构")
public class PageQuery<T extends Enum<T> & IColumnEnum> implements Serializable {
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
     * 枚举类型，编写规则参考 {@link DefaultSortColumnEnum}
     **/
    @ApiModelProperty(value = "需要排序的字段名称", example = "createTime")
    private T columnName;

    /**
     * 排序类型
     **/
    @ApiModelProperty(value = "排序类型，升序：ASC，降序：DESC", example = "DESC")
    private DefaultSrotOrderEnum order;

    /**
     * 排序sql后缀
     * <p>
     * {@link JsonIgnore} 序列化和反序列化时忽略该字段，基于Jackson
     * <p>
     * {@code @ApiModelProperty(hidden = true)} 不在Swagger界面上展示
     **/
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String sortSqlSuffix;

    /**
     * 分页信息转化为 Mybatis-Plus 的分页信息封装类 {@link Page}
     * <p>
     * 为了防止发生反省丢失问题，需要指定泛型类类型作为参数
     *
     * @param e   实体类的类对象
     * @param <E> 泛型类
     * @return Mybatis-Plus 的分页信息封装类 {@link Page}
     **/
    public <E> Page<E> toPage(Class<E> e) {
        return new Page<>(current, size);
    }

    /**
     * 重写 {@link PageQuery#sortSqlSuffix} 属性的get方法
     * <p>
     * 用空格拼接 {@link PageQuery#columnName} 和 {@link PageQuery#order} 字段，
     *
     * @return 两者任一为空时返回按创建时间降序排序的sql后缀
     **/
    public String getSortSqlSuffix() {
        // 默认按创建时间降序排序
        String column = DefaultSortColumnEnum.CREATE_TIME.getName();
        String order = DefaultSrotOrderEnum.DESC.getValue();
        if (Objects.nonNull(this.columnName) && Objects.nonNull(this.order)) {
            // 排序条件不为空，获取用户指定的排序条件
            column = this.columnName.getName();
            order = this.order.getValue();
        }
        // 使用空格拼接
        return column + " " + order;
    }

}
