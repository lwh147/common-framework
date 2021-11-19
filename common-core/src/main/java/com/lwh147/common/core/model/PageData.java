package com.lwh147.common.core.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 通用分页数据结构
 * <p>
 * {@code @JsonSerialize} 指定字段的序列化策略
 *
 * @author lwh
 * @date 2021/10/22 10:46
 **/
@Data
@Builder
@EqualsAndHashCode
@ApiModel(description = "通用分页数据结构")
public class PageData<T> implements Serializable {
    /**
     * 页大小
     **/
    @ApiModelProperty(value = "页大小，1-100（含）", required = true, example = "10")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long size;
    /**
     * 页码
     **/
    @ApiModelProperty(value = "页码，>=1", required = true, example = "1")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long current;
    /**
     * 数据总条数
     **/
    @ApiModelProperty(value = "数据总条数", example = "1000")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long total;
    /**
     * 页数
     **/
    @ApiModelProperty(value = "页数", example = "100")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pages;
    /**
     * 当前页数据
     **/
    @ApiModelProperty(value = "当前页码数据")
    private List<T> records;

    /**
     * 根据MybatisPlus的Page分页对象构建通用分页数据结构
     *
     * @param page MybatisPlus返回的Page分页对象
     * @return PageResp<T> 转换后的通用分页数据结构
     **/
    public static <T> PageData<T> fromPage(Page<T> page) {
        // 这里必须显式指定builder的泛型为T类型，与page对象的泛型保持一致
        PageDataBuilder<T> builder = builder();
        /*
         * 直接调用builder()方法获得的builder会隐式采用Object作为泛型类型从而产生类型不匹配问题
         * return new PageDataBuilder<>()，builder类型为PageDataBuilder<Object>
         */
        return builder
                .size(page.getSize())
                .current(page.getCurrent())
                .total(page.getTotal())
                .pages(page.getPages())
                .records(page.getRecords())
                .build();
    }
}
