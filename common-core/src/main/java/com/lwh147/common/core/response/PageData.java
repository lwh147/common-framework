package com.lwh147.common.core.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 通用分页响应结果封装
 *
 * @author lwh
 * @date 2021/10/22 10:46
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "通用分页响应结果封装")
public class PageData<T> implements Serializable {
    /**
     * 页大小
     **/
    @ApiModelProperty(value = "页大小，[1-100]", required = true, example = "10")
    private Long size;
    /**
     * 页码
     **/
    @ApiModelProperty(value = "页码，>=1", required = true, example = "1")
    private Long current;
    /**
     * 数据总条数
     **/
    @ApiModelProperty(value = "数据总条数", example = "1000")
    private Long total;
    /**
     * 页数
     **/
    @ApiModelProperty(value = "页数", example = "100")
    private Long pages;
    /**
     * 当前页数据
     **/
    @ApiModelProperty(value = "当前页数据")
    private List<T> records;

    /**
     * 根据 MybatisPlus 的 {@link IPage} 分页对象构建通用分页数据对象
     *
     * @param page MybatisPlus 内置的 {@link IPage} 分页对象
     * @return 使用 {@link PageData} 封装的通用分页数据对象
     **/
    public static <T> PageData<T> fromPage(IPage<T> page) {
        /*
         * 泛型方法调用时可指定泛型类型：PageData.<T>builder()
         * 这里显式指定builder方法的泛型为T，与page的泛型类型保持一致，避免泛型丢失问题
         * 如果直接调用builder方法而不指定泛型，会隐式采用Object作为泛型类型从而产生类型不匹配的问题
         */
        return PageData.<T>builder()
                .size(page.getSize())
                .current(page.getCurrent())
                .total(page.getTotal())
                .pages(page.getPages())
                .records(page.getRecords())
                .build();
    }
}