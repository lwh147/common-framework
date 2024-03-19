package com.lwh147.common.core.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lwh147.common.core.enums.BaseSortColumnEnum;
import com.lwh147.common.core.enums.ColumnEnum;
import com.lwh147.common.core.enums.SortOrderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 可指定排序列和排序类型的分页查询参数封装
 *
 * @param <T> 排序字段名称，<strong>表列名枚举类对象</strong>，必须实现 {@link ColumnEnum} 接口
 *            <p>
 *            使用枚举是为了限制用户输入，<strong>一定程度上预防SQL注入</strong>
 *            <p>
 *            详细参考 {@link BaseSortColumnEnum}
 * @author lwh
 * @apiNote 必须作为 <strong>请求体</strong> 接受参数，否则数据绑定时枚举类型不能被识别，报 {@link IllegalArgumentException}
 * 错误
 * @date 2021/10/22 10:44
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "可指定排序列的分页查询参数封装")
public class SortPageQuery<T extends Enum<T> & ColumnEnum> extends PageQuery implements Serializable {
    /**
     * 排序字段
     **/
    @ApiModelProperty(value = "排序字段", example = "createTime")
    private T column;

    /**
     * 排序类型
     **/
    @ApiModelProperty(value = "排序类型", example = "DESC")
    private SortOrderEnum order;

    /**
     * 排序sql后缀，即 ORDER BY 后的内容，基于指定的排序字段和排序类型生成
     * <p>
     * {@link JsonIgnore} 序列化和反序列化时忽略该字段，基于 Jackson
     * <p>
     * {@code @ApiModelProperty(hidden = true)} 不在 Swagger 界面上展示
     **/
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String sortSqlSuffix;

    /**
     * 重写 {@link SortPageQuery#sortSqlSuffix} 属性的get方法，用空格拼接排序字段和排序类型后返回，便于使用
     * <p>
     * 如果 {@link SortPageQuery#column} 为空则返回 {@code null}，如果 {@link SortPageQuery#order} 为空则不指定排序类型
     *
     * @return 用空格拼接 {@link SortPageQuery#column} 和 {@link SortPageQuery#order} 后的字符串或 {@code null}
     **/
    public String getSortSqlSuffix() {
        if (this.column == null) {
            return null;
        }
        return this.column.getColumnName() + (this.order == null ? "" : " " + this.order.getValue());
    }

}