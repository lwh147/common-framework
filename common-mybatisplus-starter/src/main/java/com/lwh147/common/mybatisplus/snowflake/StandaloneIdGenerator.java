package com.lwh147.common.mybatisplus.snowflake;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lwh147.common.mybatisplus.snowflake.service.Worker;

/**
 * 单机模式下的ID生成器
 *
 * @author lwh
 * @date 2021/11/26 14:30
 **/
public class StandaloneIdGenerator implements IdentifierGenerator {
    /**
     * 雪花算法对象
     **/
    private final Snowflake snowflake;

    /**
     * 单机模式下数据中心统一为 0
     **/
    public StandaloneIdGenerator() {
        this.snowflake = new Snowflake(Worker.DEFAULT_WORKER_ID, Worker.DEFAULT_DATA_CENTER_ID);
    }

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }

    public Long nextId() {
        return snowflake.nextId();
    }
}
