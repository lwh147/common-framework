package com.lwh147.common.mybatisplus.idgenerator;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 自定义DataCenter域的雪花算法ID生成器
 *
 * @author lwh
 * @date 2021/12/13 9:48
 **/
public class CustomIdGenerator implements IdentifierGenerator {
    /**
     * 雪花算法对象
     **/
    private final Snowflake snowflake;

    /**
     * 根据用户指定的DataCenter生成雪花算法对象
     **/
    public CustomIdGenerator(Long workerId, Long dataCenterId) {
        this.snowflake = new Snowflake(workerId, dataCenterId);
    }

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }

    public Long nextId() {
        return snowflake.nextId();
    }
}