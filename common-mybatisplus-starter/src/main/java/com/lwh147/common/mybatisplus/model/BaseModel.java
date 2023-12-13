package com.lwh147.common.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwh147.common.model.constant.DateTimeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 基础实体类，只包含必须存在的字段，create_time 和 update_time
 * <p>
 * 在关系型数据库中数据表之间存在一对一、一对多、多对多的关系，在处理多对多映射关系的时候，通常会引入第三张表将多对多关系分解为
 * 两个一对多的关系，而引入的第三张表中可能存储的仅仅是两个表数据之间的对应关系，所以这种表可以称为关系表，由于关系表中存储的不
 * 是系统中最基本的数据，所以关系表多是多主键且可以不具有逻辑删除、版本控制等功能，继承本基础实体类即可
 * <p>
 * 但是其它数据表除了必须有上述字段外通常需要具备主键ID和逻辑删除字段，为此我也提供了 {@link DataModel}，数据表实体类可以选择
 * 继承此类
 * <p>
 * 你也可以根据情况选择继承该类与否，如果想完全自定义公共字段，那么可以直接
 * 继承 {@link Model} 类来编写自己的实体类基类
 *
 * @param <T> 必须是继承了 {@code BaseModel} 的实体类
 * @author lwh
 * @date 2021/11/25 17:17
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseModel<T extends BaseModel<T>> extends Model<T> {
    /**
     * 创建时间
     * <p>
     * 在代码中配置全局统一初始化以减轻数据库负担
     * <p>
     * {@code @JsonFormat} 指定序列化和反序列化时采用的日期时间格式，基于Jackson
     **/
    @TableField(value = CREATE_TIME, fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    private Date createTime;
    /**
     * 修改时间
     * <p>
     * 在代码中配置全局统一更新以减轻数据库负担
     **/
    @TableField(value = UPDATE_TIME, fill = FieldFill.UPDATE)
    @DateTimeFormat(pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    private Date updateTime;

    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
}