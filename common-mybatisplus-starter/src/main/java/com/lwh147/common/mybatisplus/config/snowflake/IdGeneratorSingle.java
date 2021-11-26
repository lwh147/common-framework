package com.lwh147.common.mybatisplus.config.snowflake;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 单实例下的ID生成策略
 *
 * @author lwh
 * @date 2021/11/26 14:30
 **/
public class IdGeneratorSingle implements IdentifierGenerator {
    /**
     * 默认单实例下的雪花算法对象
     **/
    private final Snowflake snowflake;

    /**
     * 单实例模式下默认都是0
     **/
    public IdGeneratorSingle() {
        this.snowflake = new Snowflake(Worker.DEFAULT_WORKER_ID, Worker.DEFAULT_DATA_CENTER_ID);
    }

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }
}
