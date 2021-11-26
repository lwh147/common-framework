package com.lwh147.common.mybatisplus.config.snowflake;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 多实例下的ID生成策略
 *
 * @author lwh
 * @date 2021/11/26 17:16
 **/
public class IdGeneratorMulti implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return null;
    }
}
