package com.lwh147.common.core.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页请求参数封装
 *
 * @author lwh
 * @date 2021/10/22 10:44
 **/
@Data
@EqualsAndHashCode
@ApiModel(description = "分页请求参数封装")
public class PageQuery implements Serializable {
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
    @Max(value = 100L, message = "页大小必须为[1, 100]的整数")
    @Min(value = 1L, message = "页大小必须为[1, 100]的整数")
    @NotNull(message = "页大小不能为空")
    @ApiModelProperty(value = "页大小，[1, 100]", required = true, example = "10")
    private Long size;

    /**
     * 将分页参数对象转化为 MybatisPlus 的 {@link Page} 类型分页对象，需指定分页数据类型
     *
     * @param e 分页数据类型
     * @return MybatisPlus 的 {@link Page} 类型分页对象
     **/
    public <E> Page<E> toPage(Class<E> e) {
        return new Page<>(current, size);
    }

    /**
     * 将分页参数对象转化为 MybatisPlus 的 {@link Page} 类型分页对象
     *
     * @return MybatisPlus 的 {@link Page} 类型分页对象
     * @apiNote 不推荐使用此方法，会产生泛型丢失的问题
     **/
    public Page<?> toPage() {
        return new Page<>(current, size);
    }
}