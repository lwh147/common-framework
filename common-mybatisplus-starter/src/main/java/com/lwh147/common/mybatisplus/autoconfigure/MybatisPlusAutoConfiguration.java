package com.lwh147.common.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lwh147.common.mybatisplus.snowflake.MicroserviceIdGenerator;
import com.lwh147.common.mybatisplus.snowflake.MonolithIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * MybaitsPlus 配置类
 *
 * @author lwh
 * @date 2021/11/25 16:47
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisPlusAutoConfiguration {
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
        if (mybatisPlusProperties.getEnabled()) {
            log.debug("配置并开启自定义ID生成策略[微服务应用模式]");
            return new MicroserviceIdGenerator();
        }
        log.debug("配置并开启自定义ID生成策略[单体应用模式]");
        return new MonolithIdGenerator();
    }
}
