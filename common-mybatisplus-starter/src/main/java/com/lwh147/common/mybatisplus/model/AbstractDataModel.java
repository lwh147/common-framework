package com.lwh147.common.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 数据实体类，在基础实体类上增加主键ID和逻辑删除
 *
 * @param <T> 继承了 {@code AbstractDataModel} 的实体类
 * @author lwh
 * @date 2021/11/25 16:48
 **/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDataModel<T extends AbstractDataModel<T>> extends BaseModel<T> {
    /**
     * 主键，雪花算法生成
     **/
    @TableId(value = ID, type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 逻辑删除
     **/
    @TableLogic(value = "0", delval = "1")
    @TableField(value = DELETED)
    private Boolean deleted;

    public static final String ID = "id";
    public static final String DELETED = "deleted";
}