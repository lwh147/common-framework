package com.lwh147.common.mybatisplus.snowflake;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 微服务模式下的ID生成策略
 *
 * @author lwh
 * @date 2021/11/26 17:16
 **/
public class MicroserviceIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return null;
    }
}
