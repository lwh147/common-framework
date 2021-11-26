package com.lwh147.common.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 数据实体类，在基础实体类上增加逻辑删除和版本控制
 * <p>
 * 对于基础数据表的删除和修改需要严格控制
 *
 * @param <T> 继承了 {@code DataModel} 的实体类
 * @author lwh
 * @date 2021/11/25 16:48
 **/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DataModel<T extends DataModel<?>> extends BaseModel<T> {
    /**
     * 逻辑删除
     **/
    @TableLogic
    @TableField(DELETED)
    protected Integer deleted;
    /**
     * 数据版本
     **/
    @Version
    @TableField(VERSION)
    protected Integer version;

    public static final String DELETED = "deleted";
    public static final String VERSION = "version";
}
