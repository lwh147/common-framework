package com.lwh147.common.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JetCache配置类
 *
 * @author lwh
 * @date 2021/12/7 17:37
 **/
@Data
@ConfigurationProperties(prefix = "jetcache")
public class JetCacheProperties {
    /**
     * 数据库，默认1号，与RedisTemplate区分
     **/
    private Integer database = 1;
    /**
     * 本地缓存数量限制，默认1024个
     **/
    private Integer localLimit = 1024;
    /**
     * 本地缓存过期时间，默认1分钟 60s
     **/
    private Integer localExpiredIn = 60;
    /**
     * 远程缓存过期时间，默认3分钟 180s
     **/
    private Integer remoteExpiredIn = 180;
    /**
     * 缓存使用情况统计时间间隔，默认 15 分钟
     **/
    private Integer statIntervalMinutes = 15;
}
