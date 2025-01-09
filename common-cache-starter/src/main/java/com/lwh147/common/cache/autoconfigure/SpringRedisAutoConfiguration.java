package com.lwh147.common.cache.autoconfigure;

import com.lwh147.common.cache.policy.CacheKeyConverter;
import com.lwh147.common.cache.policy.RedisKeySerializer;
import com.lwh147.common.cache.policy.RedisValueSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Spring Data Redis 配置
 * <p>
 * value采用 Jackson 泛型序列化策略 {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer}
 * <p>
 * key采用自定义序列化策略 {@link CacheKeyConverter#convert(Object)}
 * <p>
 * {@link RedisTemplate} 默认永不过期， {@link RedisCacheManager} 默认3分钟过期
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
    @Resource
    private RedisProperties redisProperties;

    /**
     * 自定义 RedisTemplate，覆盖默认的 RedisTemplate
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate() {
        // 新建RedisTemplate
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 默认序列化工具采用泛型Jackson序列化工具
        template.setDefaultSerializer(RedisValueSerializer.INSTANCE);
        // key采用String序列化工具
        template.setKeySerializer(RedisKeySerializer.INSTANCE);
        // value采用泛型Jackson序列化工具
        template.setValueSerializer(RedisValueSerializer.INSTANCE);
        // hash key采用泛型Jackson序列化工具，因为hash key 可能会被作为entry set返回，需要保持反序列化结果的一致性
        template.setHashKeySerializer(RedisValueSerializer.INSTANCE);
        // hash value采用泛型Jackson序列化工具
        template.setHashValueSerializer(RedisValueSerializer.INSTANCE);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 自定义 CacheManager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        // 生成默认配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // 修改默认配置
        redisCacheConfiguration = redisCacheConfiguration
                // 采用String序列化工具
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisKeySerializer.STRING_SERIALIZER))
                // value序列化策略采用泛型Jackson
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisValueSerializer.INSTANCE))
                // 默认永不过期，这里改为默认三分钟过期
                .entryTtl(redisProperties.getTimeout() == null ? Duration.of(3, ChronoUnit.MINUTES) : redisProperties.getTimeout());
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}