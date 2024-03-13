package com.lwh147.common.mybatisplus.schema;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 使用乐观锁的数据实体类，在数据实体类基础上增加乐观锁字段
 *
 * @param <T> 继承了 {@code VersionModel} 的实体类
 * @author lwh
 * @date 2023/12/13 10:15
 **/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class VersionModel<T extends VersionModel<T>> extends DataModel<T> {
    /**
     * 数据版本
     **/
    @Version
    @TableField(value = VERSION)
    private Integer version;

    public static final String VERSION = "version";
}