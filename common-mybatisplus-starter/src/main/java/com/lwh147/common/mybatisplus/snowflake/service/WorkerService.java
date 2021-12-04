package com.lwh147.common.mybatisplus.snowflake.service;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 微服务模式下获取当前实例的工作机器ID和其所属数据中心ID的服务类
 *
 * @author lwh
 * @date 2021/11/25 16:32
 **/
@Component
public class WorkerService {
    private Worker worker = new Worker(Worker.DEFAULT_WORKER_ID, Worker.DEFAULT_DATA_CENTER_ID);
    public static final String CAHCE_PREFIX = "sys:snowflake:";

    public Worker getWorker() {
        // 从缓存中获取
        // 判断是否为null，如果是，直接返回创建的默认信息
        // 不是则自增一返回
        if (Objects.nonNull(this.worker)) {
            return this.worker;
        }
        return null;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
        // 设置缓存
    }
}
