package com.lwh147.common.mybatisplus.generator.code;

/**
 * 表类型枚举，决定要生成的表实体类继承的基础实体类型
 *
 * @author lwh
 * @date 2024/05/10 17:07
 **/
public enum TableTypeEnum {
    /**
     * 继承 {@link com.lwh147.common.mybatisplus.model.BaseModel}
     **/
    BASE,
    /**
     * 继承 {@link com.lwh147.common.mybatisplus.model.AbstractDataModel}
     **/
    DATA,
    /**
     * 继承 {@link com.lwh147.common.mybatisplus.model.AbstractVersionModel}
     **/
    VERSION
}
