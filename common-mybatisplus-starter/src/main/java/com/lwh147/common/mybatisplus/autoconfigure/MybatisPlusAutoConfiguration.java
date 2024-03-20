package com.lwh147.common.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.mybatisplus.generator.id.ClusterIdGenerator;
import com.lwh147.common.mybatisplus.generator.id.CustomIdGenerator;
import com.lwh147.common.mybatisplus.generator.id.StandaloneIdGenerator;
import com.lwh147.common.mybatisplus.properties.MybatisPlusProperties;
import com.lwh147.common.mybatisplus.properties.SnowflakeProperties;
import com.lwh147.common.mybatisplus.properties.enums.SnowflakeIdType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * MybaitsPlus 自动配置类
 *
 * @author lwh
 * @date 2021/11/25 16:47
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({SnowflakeProperties.class, MybatisPlusProperties.class})
public class MybatisPlusAutoConfiguration {
    @Resource
    private MybatisPlusProperties mybatisPlusProperties;
    @Resource
    private SnowflakeProperties snowflakeProperties;

    /**
     * 自定义雪花算法ID生成器配置，配置中开启且未配置其它ID生成器时生效
     **/
    @Bean
    @ConditionalOnMissingBean(IdentifierGenerator.class)
    @ConditionalOnProperty(name = "snowflake.enabled", havingValue = "true", matchIfMissing = true)
    public IdentifierGenerator idGenerator() {
        SnowflakeIdType snowflakeIdType = snowflakeProperties.getIdType();
        if (SnowflakeIdType.STANDALONE.equals(snowflakeIdType)) {
            log.debug("配置并开启雪花算法ID生成器[单机模式]");
            return new StandaloneIdGenerator();
        } else if (SnowflakeIdType.CLUSTER.equals(snowflakeIdType)) {
            log.debug("配置并开启雪花算法ID生成器[集群模式]");
            return new ClusterIdGenerator();
        } else {
            Long workerId = snowflakeProperties.getWorkerId();
            Long dataCenterId = snowflakeProperties.getDataCenterId();
            if (workerId != null && dataCenterId != null) {
                log.debug("配置并开启雪花算法ID生成器[定制模式]");
                return new CustomIdGenerator(workerId, dataCenterId);
            }
            log.warn("配置了雪花算法ID生成器为[定制模式]但没有指定工作机器ID和数据中心ID，将使用默认的[单机模式]");
            return new StandaloneIdGenerator();
        }
    }

    /**
     * MybaitsPlus 拦截器配置，用户未进行配置时生效
     **/
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页插件
        if (mybatisPlusProperties.getPage().getEnabled()) {
            // 读取分页配置
            MybatisPlusProperties.Page pageInfo = mybatisPlusProperties.getPage();
            // 指定数据库类型
            PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.getDbType(pageInfo
                    .getDbType().getType()));
            // 如果存在方言则指定方言类型
            Class<? extends IDialect> dialectClass = pageInfo.getDbType().getDialect();
            if (dialectClass != null) {
                try {
                    // 指定数据库方言
                    pageInterceptor.setDialect(dialectClass.newInstance());
                } catch (Exception e) {
                    throw CommonExceptionEnum.COMMON_ERROR.toException("实例化数据库方言类[" + pageInfo.getDbType()
                            .getDialect().toString() + "]时发生异常", e);
                }
            }
            // 左连接优化策略
            pageInterceptor.setOptimizeJoin(pageInfo.getEnableOptimizeJoin());
            interceptor.addInnerInterceptor(pageInterceptor);
            log.debug("配置并开启MybaitsPlus分页插件");
        }

        // 乐观锁
        if (mybatisPlusProperties.getEnableOptimisticLocker()) {
            OptimisticLockerInnerInterceptor optimisticLockerInterceptor = new OptimisticLockerInnerInterceptor();
            interceptor.addInnerInterceptor(optimisticLockerInterceptor);
            log.debug("配置并开启MybaitsPlus乐观锁");
        }

        // 防止全表更新与删除
        if (mybatisPlusProperties.getEnableBlockAttackCheck()) {
            BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
            interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
            log.debug("配置并开启MybaitsPlus防止全表更新与删除策略");
        }

        // 拦截垃圾sql
        if (mybatisPlusProperties.getEnableIllegalSqlCheck()) {
            IllegalSQLInnerInterceptor illegalSqlInterceptor = new IllegalSQLInnerInterceptor();
            interceptor.addInnerInterceptor(illegalSqlInterceptor);
            log.debug("配置并开启MybaitsPlus拦截垃圾sql策略");
        }

        return interceptor;
    }
}