package com.lwh147.common.mybatisplus.snowflake;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lwh147.common.mybatisplus.snowflake.service.Worker;

/**
 * 单体应用模式下的ID生成策略
 *
 * @author lwh
 * @date 2021/11/26 14:30
 **/
public class MonolithIdGenerator implements IdentifierGenerator {
    /**
     * 雪花算法对象
     **/
    private final Snowflake snowflake;

    /**
     * 单体模式下数据中心统一为 0
     **/
    public MonolithIdGenerator() {
        this.snowflake = new Snowflake(Worker.DEFAULT_WORKER_ID, Worker.DEFAULT_DATA_CENTER_ID);
    }

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }
}
