package com.lwh147.common.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwh147.common.util.constant.DateTimeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 基础实体类，只包含必须存在的字段，主键id，create_time 和 update_time
 * <p>
 * 某些重要的数据表除了必须有上述字段外还需要具备逻辑删除字段，可以选择继承此类 {@link BaseDataModel}
 * <p>
 * 如果想更进一步对数据表的更新进行并发控制，需要使用到乐观锁，可以选择继承此类 {@link BaseDataVersionModel}
 * <p>
 * 当然如果想完全自定义公共字段，那么可以直接继承 {@link Model} 类或编写自己的实体类基类再继承
 *
 * @param <T> 必须是继承了 {@link BaseModel} 的实体类
 * @author lwh
 * @date 2021/11/25 17:17
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseModel<T extends BaseModel<T>> extends Model<T> {
    /**
     * 主键，雪花算法生成
     **/
    @TableId(value = ID, type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 创建时间
     *
     * @apiNote 在代码中配置全局统一初始化以减轻数据库负担
     * <p>
     * {@code @JsonFormat} 指定序列化和反序列化时采用的日期时间格式，基于Jackson
     **/
    @TableField(value = CREATE_TIME, fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    private Date createTime;
    /**
     * 修改时间
     **/
    @TableField(value = UPDATE_TIME, fill = FieldFill.UPDATE)
    @DateTimeFormat(pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    @JsonFormat(timezone = DateTimeConstant.DEFAULT_TIMEZONE, pattern = DateTimeConstant.DEFAULT_DATETIME_PATTERN)
    private Date updateTime;

    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME_CC = "createTime";
    public static final String UPDATE_TIME_CC = "updateTime";
}