package com.lwh147.common.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lwh147.common.core.enums.ICommonEnum;
import com.lwh147.common.core.enums.OrderEnum;
import com.lwh147.common.core.exception.CommonExceptionEnum;
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
 *            {@link ICommonEnum} 接口
 *            <p>
 *            使用枚举是为了限制用户输入，<strong>防止SQL注入</strong>
 *            <p>
 *            详细参考内部类 {@link DefaultSortColumnEnum}
 * @author lwh
 * @apiNote 必须作为<strong>请求体</strong>接受参数，否则数据绑定时
 * 枚举类型不能被识别，报 {@link IllegalArgumentException}
 * 错误
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
     * 枚举类型，编写规则参考 {@link DefaultSortColumnEnum}
     **/
    @ApiModelProperty(value = "需要排序的字段名称", example = "createTime")
    private T columnName;

    /**
     * 排序类型
     **/
    @ApiModelProperty(value = "排序类型，升序：ASC，降序：DESC", example = "DESC")
    private OrderEnum order;

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
     * 重写 {@link PageQuery#sortSqlSuffix} 属性的get方法
     * <p>
     * 用空格拼接 {@link PageQuery#columnName} 和 {@link PageQuery#order} 字段，
     *
     * @return 两者任一为空时返回按创建时间降序排序的sql后缀
     **/
    public String getSortSqlSuffix() {
        // 默认按创建时间降序排序
        String column = DefaultSortColumnEnum.CREATE_TIME.getName();
        String order = OrderEnum.DESC.getValue();
        if (Objects.nonNull(this.columnName) && Objects.nonNull(this.order)) {
            // 排序条件不为空，获取用户指定的排序条件
            column = this.columnName.getName();
            order = this.order.getValue();
        }
        // 使用空格拼接
        return column + " " + order;
    }

    /**
     * 默认排序字段枚举类，只有创建时间
     * <p>
     * 枚举值代表前端传参取值
     * <p>
     * 枚举名称代表参数实际对应的数据库字段名
     * <p>
     * 也就是说 {@code name} 属性在这里被当作 <strong>实际的数据库字段名</strong> 使用
     * <p>
     * {@code value} 属性在这里被当作 <strong>前端传参参数值</strong> 使用
     * <p>
     * 如果自定义排序字段枚举类，必须仿照此类进行编写，这样能够避免暴露数据库字段
     * 名称到前端，增加安全性
     **/
    public enum DefaultSortColumnEnum implements ICommonEnum {
        /**
         * 默认排序字段，注意枚举值和枚举名称代表的意义
         **/
        CREATE_TIME("createTime", "create_time"),
        ;

        /**
         * 枚举值，给前端使用的字段名
         * <p>
         * {@link JsonValue} 指定序列化时使用该属性作为枚举值，基于 Jackson
         **/
        @JsonValue
        private final String value;
        /**
         * 枚举值描述，实际数据库中的字段名称
         **/
        private final String name;

        /**
         * 构造方法，默认私有
         **/
        DefaultSortColumnEnum(String value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值寻找枚举对象
         * <p>
         * {@link JsonCreator} 指定使用此方法进行反序列化，基于Jackson
         *
         * @return 找到的枚举对象，没找到抛出异常
         * @throws com.lwh147.common.core.exception.CommonException
         **/
        @JsonCreator
        public static DefaultSortColumnEnum fromValue(String value) {
            for (DefaultSortColumnEnum e : DefaultSortColumnEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
            throw CommonExceptionEnum.CLIENT_ARGUMENT_NOT_VALID_ERROR.toException("[" + value + "]不是允许的排序字段");
        }

        /**
         * 判断枚举值是否存在
         *
         * @return 匹配的枚举对象，不存在返回 {@code null}
         **/
        public static DefaultSortColumnEnum exist(String value) {
            for (DefaultSortColumnEnum e : DefaultSortColumnEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
            return null;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
