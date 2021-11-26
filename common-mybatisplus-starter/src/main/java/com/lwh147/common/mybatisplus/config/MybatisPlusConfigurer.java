package com.lwh147.common.mybatisplus.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lwh147.common.mybatisplus.config.properties.MybatisPlusProperties;
import com.lwh147.common.mybatisplus.config.snowflake.IdGeneratorMulti;
import com.lwh147.common.mybatisplus.config.snowflake.IdGeneratorSingle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 配置 MybaitsPlus
 *
 * @author lwh
 * @date 2021/11/25 16:47
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisPlusConfigurer {
    @Resource
    private MybatisPlusProperties mybatisPlusProperties;

    /**
     * 雪花算法ID生成策略配置，生效条件：
     * <p>
     * 未配置其它ID生成器
     **/
    @Bean
    @ConditionalOnMissingBean(IdentifierGenerator.class)
    public IdentifierGenerator idGenerator() {
        if (mybatisPlusProperties.getSingle()) {
            log.debug("配置并开启自定义ID生成策略[单实例模式]");
            return new IdGeneratorSingle();
        }
        log.debug("配置并开启自定义ID生成策略[多实例模式]");
        return new IdGeneratorMulti();
    }
}
