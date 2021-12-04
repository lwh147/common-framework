package com.lwh147.common.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwh147.common.core.constant.DateTimeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 基础实体类，只包含绝对必须存在的字段，id、create_time 和 update_time
 * <p>
 * 在关系型数据库中数据表之间存在一对一、一对多、多对多的关系，在处理多对
 * 多映射关系的时候，通常会引入第三张表将多对多关系分解为两个一对多的关系，
 * 而引入的第三张表中可能存储的仅仅是两个表数据之间的对应关系，所以这种表
 * 可以称为关系表，由于关系表中存储的不是系统中最基本的数据，所以关系表可
 * 以不具有逻辑删除、版本控制等功能，继承本基础实体类即可
 * <p>
 * 但是其它数据表除了必须有上述字段外通常也必须具备逻辑删除和版本控制字段，
 * 为此我也提供了 {@link DataModel}，数据表实体类可以选择继承此类
 * <p>
 * 最后，如果想完全自定义公共字段，那么可以直接继承 {@link Model} 类来
 * 编写自己的实体类基类
 *
 * @param <T> 继承了 {@code BaseModel} 的实体类
 * @author lwh
 * @date 2021/11/25 17:17
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseModel<T extends BaseModel<?>> extends Model<T> {
    /**
     * 主键，雪花算法生成
     **/
    @TableField(ID)
    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;
    /**
     * 创建时间
     * <p>
     * {@code @JsonFormat} 指定序列化和反序列化时采用的日期时间格式，基于Jackson
     **/
    @TableField(CREATE_TIME)
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_FORMAT)
    protected Date createTime;
    /**
     * 修改时间
     **/
    @TableField(UPDATE_TIME)
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_FORMAT)
    protected Date updateTime;

    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
}
