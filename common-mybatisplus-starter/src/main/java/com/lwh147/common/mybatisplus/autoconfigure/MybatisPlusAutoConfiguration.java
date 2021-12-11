package com.lwh147.common.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lwh147.common.mybatisplus.properties.SnowflakeProperties;
import com.lwh147.common.mybatisplus.snowflake.ClusterIdGenerator;
import com.lwh147.common.mybatisplus.snowflake.StandaloneIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@EnableConfigurationProperties(SnowflakeProperties.class)
public class MybatisPlusAutoConfiguration {
    @Resource
    private SnowflakeProperties snowflakeProperties;
    @Resource
    private MybatisPlusProperties mybatisPlusProperties;

    /**
     * 自定义雪花算法ID生成器配置，生效条件：
     * <p>
     * 配置中开启
     * <p>
     * 未配置其它ID生成器
     **/
    @Bean
    @ConditionalOnMissingBean(IdentifierGenerator.class)
    @ConditionalOnProperty(name = "snowflake.enabled", havingValue = "true", matchIfMissing = true)
    public IdentifierGenerator idGenerator() {
        if (SnowflakeProperties.STANDALONE.equals(snowflakeProperties.getDataCenter())) {
            log.debug("配置并开启雪花算法ID生成器[单机模式]");
            return new StandaloneIdGenerator();
        } else if (SnowflakeProperties.CLUSTER.equals(snowflakeProperties.getDataCenter())) {
            log.debug("配置并开启雪花算法ID生成器[集群模式]");
            return new ClusterIdGenerator();
        } else {
            log.warn("配置项[snowflake.data-center]取值错误");
            log.debug("配置并开启雪花算法ID生成器[单机模式]");
            return new StandaloneIdGenerator();
        }
    }

    /**
     * 新版配置方法
     **/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        this.paginationInnerInterceptor(interceptor);
        this.optimisticLockerInterceptor(interceptor);
        return interceptor;
    }

    /**
     * 配置开启自动分页
     **/
    private void paginationInnerInterceptor(MybatisPlusInterceptor interceptor) {
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        pageInterceptor.setDialect();
        interceptor.addInnerInterceptor(pageInterceptor);
    }

    /**
     * 配置开启乐观锁
     **/
    private void optimisticLockerInterceptor(MybatisPlusInterceptor interceptor) {
        OptimisticLockerInnerInterceptor optimisticLockerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(optimisticLockerInterceptor);
    }
}
