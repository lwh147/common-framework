package com.lwh147.common.mybatisplus.snowflake;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lwh147.common.mybatisplus.snowflake.service.SnowflakeService;

import javax.annotation.Resource;

/**
 * 集群模式下的ID生成器
 * <p>
 * 各工作机器竞争抢占注册自己的工作机器ID和所属数据中心ID
 *
 * @author lwh
 * @date 2021/11/26 17:16
 **/
public class ClusterIdGenerator implements IdentifierGenerator {
    @Resource
    private SnowflakeService snowflakeService;

    @Override
    public Number nextId(Object entity) {
        return snowflakeService.nextId();
    }

    public Long nextId() {
        return snowflakeService.nextId();
    }
}
