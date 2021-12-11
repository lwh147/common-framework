package com.lwh147.common.mybatisplus.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义雪花算法ID生成策略配置项
 *
 * @author lwh
 * @date 2021/11/26 16:41
 **/
@Data
@ConfigurationProperties(prefix = "snowflake")
public class SnowflakeProperties {
    /**
     * 是否开启雪花算法ID生成，默认：true
     **/
    private Boolean enabled = true;
    /**
     * 单机(standalone)模式还是集群(cluster)模式，默认为：standalone
     **/
    private String dataCenter = STANDALONE;

    /**
     * 单机模式
     **/
    public static final String STANDALONE = "standalone";
    /**
     * 集群模式
     **/
    public static final String CLUSTER = "cluster";
}
