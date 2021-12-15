package com.lwh147.common.mybatisplus.properties;

import com.lwh147.common.mybatisplus.properties.enums.SnowflakeIdType;
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
     * 是否开启雪花算法ID生成，默认为 {@code true}
     **/
    private Boolean enabled = true;
    /**
     * 雪花算法ID生成类型，默认为 {@link SnowflakeIdType#STANDALONE}
     **/
    private SnowflakeIdType idType = SnowflakeIdType.STANDALONE;
    /**
     * 工作机器ID，仅在 {@link SnowflakeIdType#CUSTOMIZED} 模式下有效
     **/
    private Long workerId;
    /**
     * 数据中心ID，仅在 {@link SnowflakeIdType#CUSTOMIZED} 模式下有效
     **/
    private Long dataCenterId;
}
