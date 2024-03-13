package com.lwh147.common.mybatisplus.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lwh147.common.mybatisplus.schema.BaseModel;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 数据实体类通用字段填充处理器实现类
 *
 * @author lwh
 * @date 2023-12-23 16:42:27
 **/
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间
        this.strictInsertFill(metaObject, BaseModel.CREATE_TIME_CC, Date.class, new Date());  // 起始版本 3.3.0(推荐)
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 填充更新时间
        this.strictUpdateFill(metaObject, BaseModel.UPDATE_TIME_CC, Date.class, new Date());  // 起始版本 3.3.0(推荐)
    }
}