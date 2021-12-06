package com.lwh147.common.cache.autoconfigure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * Spring Data Redis 配置
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
     * 自定义 RedisTemplate 覆盖默认的redisTemplate
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        // 新建RedisTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // value序列化策略采用Jackson
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // json与java对象属性不全对应时也进行反序列化
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // String序列化工具
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        // key采用String序列化工具
        template.setKeySerializer(stringSerializer);
        // hash的key也采用String序列化工具
        template.setHashKeySerializer(stringSerializer);
        // String类型的数据采用String序列化工具
        template.setStringSerializer(stringSerializer);
        // value采用Jackson序列化工具
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value也采用Jackson序列化工具
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 默认序列化工具为Jackson
        template.setDefaultSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        log.debug("配置并注入RedisTemplate<String, Object>，key序列化工具为：StringRedisSerializer, value序列化工具为：Jackson2JsonRedisSerializer");
        return template;
    }

    /**
     * 自定义 CacheManager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        // String序列化工具
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // value序列化策略采用Jackson
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // json与java对象属性不全对应时也进行反序列化
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 生成默认配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // 修改默认配置
        redisCacheConfiguration = redisCacheConfiguration
                // 设置缓存过期时间为3小时
                .entryTtl(Duration.ofHours(3))
                // 采用String序列化工具
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer))
                // value序列化策略采用Jackson
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                // 不缓存null值
                .disableCachingNullValues();
        log.debug("配置CacheManager，key序列化工具为：StringRedisSerializer, value序列化工具为：Jackson2JsonRedisSerializer，默认过期时间3小时");
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
