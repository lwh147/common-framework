package com.lwh147.common.mybatisplus.generator.code;

import com.lwh147.common.mybatisplus.model.BaseDataModel;
import com.lwh147.common.mybatisplus.model.BaseDataVersionModel;
import com.lwh147.common.mybatisplus.model.BaseModel;

/**
 * 表类型枚举，要生成的表实体类继承的基础实体类型
 *
 * @author lwh
 * @date 2024/05/10 17:07
 **/
public enum TableTypeEnum {
    /**
     * 继承 {@link BaseModel}
     **/
    BASE,
    /**
     * 继承 {@link BaseDataModel}
     **/
    DATA,
    /**
     * 继承 {@link BaseDataVersionModel}
     **/
    VERSION
}
