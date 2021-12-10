package com.lwh147.common.cache.autoconfigure;

import com.lwh147.common.cache.policy.JacksonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Spring Data Redis 配置
 * <p>
 * key采用自定义序列化策略 {@link com.lwh147.common.cache.policy.CacheKeyConverter}
 * value采用Jackson序列化策略
 * <p>
 * {@link RedisTemplate} 和 {@link RedisCacheManager} 均默认永不过期
 * <p>
 * 默认使用数据库0，与JetCache的方法缓存区分
 *
 * @author lwh
 * @date 2021/12/6 11:13
 **/
@Slf4j
@Configuration
public class SpringRedisAutoConfiguration {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 自定义 RedisTemplate，覆盖默认的 RedisTemplate
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        // 新建RedisTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // key采用String序列化工具
        template.setKeySerializer(JacksonSerializer.STRING_SERIALIZER);
        // hash的key也采用String序列化工具
        template.setHashKeySerializer(JacksonSerializer.STRING_SERIALIZER);
        // String类型的数据采用String序列化工具
        template.setStringSerializer(JacksonSerializer.STRING_SERIALIZER);
        // value采用Jackson序列化工具
        template.setValueSerializer(JacksonSerializer.JACKSON_SERIALIZER);
        // hash的value也采用Jackson序列化工具
        template.setHashValueSerializer(JacksonSerializer.JACKSON_SERIALIZER);
        // 默认序列化工具为Jackson
        template.setDefaultSerializer(JacksonSerializer.JACKSON_SERIALIZER);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 自定义 CacheManager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        // String序列化工具
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // 生成默认配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // 修改默认配置
        redisCacheConfiguration = redisCacheConfiguration
                // 采用String序列化工具
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(JacksonSerializer.STRING_SERIALIZER))
                // value序列化策略采用Jackson
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(JacksonSerializer.JACKSON_SERIALIZER))
                // 不缓存null值
                .disableCachingNullValues();
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
