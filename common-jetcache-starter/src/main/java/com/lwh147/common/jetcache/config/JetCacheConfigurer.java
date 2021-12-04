package com.lwh147.common.jetcache.config;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.embedded.LinkedHashMapCacheBuilder;
import com.alicp.jetcache.redis.lettuce.RedisLettuceCacheBuilder;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import com.lwh147.common.jetcache.convertor.JacksonKeyConvertor;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * JetCache 配置类
 * <p>
 * {@code @EnableMethodCache} 方法注解需要手动开启并指定基础扫描包路径
 *
 * @author lwh
 * @date 2021/11/25 16:47
 **/
@Slf4j
@Configuration
@EnableCreateCacheAnnotation
@AutoConfigureBefore(com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration.class)
public class JetCacheConfigurer {
    @Resource
    private RedisProperties redisProperties;

    /**
     * 基于Lettuce的redis客户端
     **/
    @Bean
    public RedisClient redisClient() {
        if (redisProperties.getHost().equals("localhost") || redisProperties.getHost().equals("127.0.0.1")) {
            // 不提供配置或配置为本地redis时给出警告
            log.warn("使用本地Redis");
        }
        // 使用Redis主机和端口号获取uri构建对象
        RedisURI.Builder uriBuilder = RedisURI.Builder.redis(redisProperties.getHost(), redisProperties.getPort());
        // 设置客户端名称，默认 default
        if (Objects.nonNull(redisProperties.getClientName())) {
            uriBuilder.withClientName(redisProperties.getClientName());
        }
        // 设置使用的数据库，默认0
        uriBuilder.withDatabase(redisProperties.getDatabase());
        // 如果密码不为空，设置密码
        if (Objects.nonNull(redisProperties.getPassword())) {
            uriBuilder.withPassword(redisProperties.getPassword());
        }
        // 是否开启SSL连接
        uriBuilder.withSsl(redisProperties.isSsl());
        RedisURI uri = uriBuilder.build();
        log.debug("Redis配置[{}]", uri);
        // 创建Redis客户端
        RedisClient client = RedisClient.create(uri);
        client.setOptions(ClientOptions.builder().
                disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .build());
        return client;
    }

    @Bean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    /**
     * JetCache配置
     **/
    @Bean
    public GlobalCacheConfig config(SpringConfigProvider configProvider, RedisClient redisClient) {
        // 创建本地缓存，默认永不过期，但是超过3小时未被访问便会过期
        Map<String, CacheBuilder> localBuilders = new HashMap<>();
        EmbeddedCacheBuilder<?> localBuilder = LinkedHashMapCacheBuilder
                .createLinkedHashMapCacheBuilder()
                .keyConvertor(JacksonKeyConvertor.INSTANCE)
                // 本地缓存在3个小时无访问的情况下失效
                .expireAfterAccess(3, TimeUnit.HOURS);
        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);
        // 创建远程缓存，默认永不过期
        Map<String, CacheBuilder> remoteBuilders = new HashMap<>();
        RedisLettuceCacheBuilder<?> remoteCacheBuilder = RedisLettuceCacheBuilder
                .createRedisLettuceCacheBuilder()
                .keyConvertor(JacksonKeyConvertor.INSTANCE)
                .valueEncoder(JavaValueEncoder.INSTANCE)
                .valueDecoder(JavaValueDecoder.INSTANCE)
                .redisClient(redisClient);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteCacheBuilder);
        // 配置JetCache
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        // 设置本地缓存
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        // 设置远程缓存
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        // 缓存使用情况统计时间间隔
        globalCacheConfig.setStatIntervalMinutes(15);
        // areaName不作为缓存key前缀
        globalCacheConfig.setAreaInCacheName(false);
        log.debug("配置并开启JetCache");
        return globalCacheConfig;
    }

}
