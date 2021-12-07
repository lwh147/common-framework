package com.lwh147.common.cache.autoconfigure;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.embedded.LinkedHashMapCacheBuilder;
import com.alicp.jetcache.redis.lettuce.RedisLettuceCacheBuilder;
import com.lwh147.common.cache.policy.CacheKeyConverter;
import com.lwh147.common.cache.policy.CacheValueDecoder;
import com.lwh147.common.cache.policy.CacheValueEncoder;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JetCache 配置类
 * <p>
 * 默认不启用 CreateCache 方式，基于缓存客户端对象的缓存操作方式推荐使用 {@link RedisTemplate}
 *
 * @author lwh
 * @date 2021/11/25 16:47
 **/
@Slf4j
@Configuration
@EnableMethodCache(basePackages = "com.lwh147")
// @AutoConfigureBefore(com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration.class)
public class JetCacheAutoConfiguration {
    @Resource
    private RedisProperties redisProperties;

    /**
     * 基于Lettuce的Redis客户端配置
     **/
    @Bean
    public RedisClient redisClient() {
        final String localhost = "localhost";
        final String localhostIp = "127.0.0.1";

        if (localhost.equals(redisProperties.getHost())
                || localhostIp.equals(redisProperties.getHost())) {
            log.warn("未提供Redis配置或使用本地Redis");
        }
        // 使用Redis主机和端口号获取uri构建对象
        RedisURI.Builder uriBuilder = RedisURI.Builder.redis(redisProperties.getHost(), redisProperties.getPort());
        // 使用的数据库，默认：0
        uriBuilder.withDatabase(redisProperties.getDatabase());
        // 密码
        if (Objects.nonNull(redisProperties.getPassword())) {
            uriBuilder.withPassword(redisProperties.getPassword());
        }
        // 客户端名称
        if (Objects.nonNull(redisProperties.getClientName())) {
            uriBuilder.withClientName(redisProperties.getClientName());
        }
        // 超时时间
        if (Objects.nonNull(redisProperties.getTimeout())) {
            uriBuilder.withTimeout(redisProperties.getTimeout());
        }
        // SSL连接
        uriBuilder.withSsl(redisProperties.isSsl());

        RedisURI uri = uriBuilder.build();
        log.debug(uri.toString());
        // 创建基于Lettuce的Redis客户端
        RedisClient client = RedisClient.create(uri);
        client.setOptions(ClientOptions
                .builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .build());
        return client;
    }

    @Bean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    /**
     * JetCache全局配置
     **/
    @Bean
    public GlobalCacheConfig config(SpringConfigProvider configProvider, RedisClient redisClient) {
        // 创建本地缓存，默认永不过期
        Map<String, CacheBuilder> localBuilders = new HashMap<>(16);
        EmbeddedCacheBuilder<?> localBuilder = LinkedHashMapCacheBuilder
                .createLinkedHashMapCacheBuilder()
                .keyConvertor(CacheKeyConverter.INSTANCE);
        // 使用默认区域名称
        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);
        // 创建基于Lettuce的Redis远程缓存，默认永不过期
        Map<String, CacheBuilder> remoteBuilders = new HashMap<>(16);
        RedisLettuceCacheBuilder<?> remoteCacheBuilder = RedisLettuceCacheBuilder
                .createRedisLettuceCacheBuilder()
                .keyConvertor(CacheKeyConverter.INSTANCE)
                .valueEncoder(CacheValueEncoder.INSTANCE)
                .valueDecoder(CacheValueDecoder.INSTANCE)
                .redisClient(redisClient);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteCacheBuilder);
        // JetCache配置类
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        // 设置本地缓存
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        // 设置远程缓存
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        // 统计时间间隔
        globalCacheConfig.setStatIntervalMinutes(15);
        // 区域名不作为缓存key前缀
        globalCacheConfig.setAreaInCacheName(false);
        log.debug("配置并开启JetCache");
        return globalCacheConfig;
    }

}
