package com.lwh147.common.mybatisplus.snowflake;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 集群模式下的ID生成器
 *
 * @author lwh
 * @date 2021/11/26 17:16
 **/
public class ClusterIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return null;
    }
}
