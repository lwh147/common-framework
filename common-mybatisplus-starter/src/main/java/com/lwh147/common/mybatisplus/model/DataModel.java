package com.lwh147.common.mybatisplus.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据实体类，在基础实体类上增加逻辑删除和版本控制
 * <p>
 * 对于基础数据表的删除和修改需要严格控制
 *
 * @param <T> 实体类
 * @author lwh
 * @date 2021/11/25 16:48
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class DataModel<T extends DataModel<?>> extends Model<T> {
    private Integer deleted;
    private Integer version;
}
