package com.lwh147.common.mybatisplus.properties.enums;

/**
 * 雪花算法ID生成类型
 *
 * @author lwh
 * @date 2021/12/13 9:32
 **/
public enum SnowflakeIdType {
    /**
     * 单机模式，算法ID中的 {@code dataCenter} 始终为 {@code 0}
     **/
    STANDALONE,
    /**
     * 集群模式，各工作机器竞争抢占注册自己的工作机器ID和所属数据中心ID
     * <p>
     * 使用远程Redis作为第三方
     **/
    CLUSTER,
    /**
     * 用户指定，需要用户为每个应用单独指定 {@code dataCenter}
     **/
    CUSTOMIZED
}