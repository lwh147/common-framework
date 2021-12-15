package com.lwh147.common.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;

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
     * 数据库，默认为 {@code 1}，与 {@link RedisTemplate} 区分
     **/
    private Integer database = 1;
    /**
     * 本地缓存数量限制，默认 {@code 1024} 个
     **/
    private Integer localLimit = 1024;
    /**
     * 本地缓存过期时间，单位秒，默认 {@code 60}
     **/
    private Long localExpiredIn = 60L;
    /**
     * 远程缓存过期时间，单位秒，默认 {@code 180}
     **/
    private Long remoteExpiredIn = 180L;
    /**
     * 缓存使用情况统计时间间隔，单位分钟，默认 {@code 15}
     **/
    private Integer statIntervalMinutes = 15;
    /**
     * 缓存刷新时间间隔，单位秒，默认 {@code 60}
     **/
    private Integer refresh = 60;
    /**
     * 停止刷新缓存的等待时间，单位秒，默认 {@code 180}
     **/
    private Integer stopRefreshAfterLastAccess = 180;
}
