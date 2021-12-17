package com.lwh147.common.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.mybatisplus.properties.MybatisPlusProperties;
import com.lwh147.common.mybatisplus.properties.SnowflakeProperties;
import com.lwh147.common.mybatisplus.properties.enums.SnowflakeIdType;
import com.lwh147.common.mybatisplus.snowflake.ClusterIdGenerator;
import com.lwh147.common.mybatisplus.snowflake.CustomizedIdGenerator;
import com.lwh147.common.mybatisplus.snowflake.StandaloneIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;

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
            if (Objects.nonNull(workerId) && Objects.nonNull(dataCenterId)) {
                log.debug("配置并开启雪花算法ID生成器[定制模式]");
                return new CustomizedIdGenerator(workerId, dataCenterId);
            }
            log.warn("配置了雪花算法ID生成器为[定制模式]但没有指定工作机器ID和数据中心ID，将使用默认的[单机模式]");
            return new StandaloneIdGenerator();
        }
    }

    /**
     * MybaitsPlus 拦截器配置
     * <p>
     * 用户未进行配置时生效
     **/
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        if (mybatisPlusProperties.getPage().getEnabled()) {
            // 配置分页插件
            MybatisPlusProperties.Page pageInfo = mybatisPlusProperties.getPage();
            // 指定数据库类型
            PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.getDbType(
                    pageInfo.getDbType().getType()));
            try {
                // 指定数据库方言
                pageInterceptor.setDialect(pageInfo.getDbType().getDialect().newInstance());
            } catch (Exception e) {
                throw CommonExceptionEnum.COMMON_ERROR.toException("实例化数据库方言类[" + pageInfo.getDbType().getDialect().toString() + "]时发生异常", e);
            }
            // 左连接优化策略
            pageInterceptor.setOptimizeJoin(pageInfo.getEnableOptimizeJoin());
            log.debug("配置并开启MybaitsPlus分页插件");
            interceptor.addInnerInterceptor(pageInterceptor);
        }

        if (mybatisPlusProperties.getEnableOptimisticLocker()) {
            // 配置乐观锁
            OptimisticLockerInnerInterceptor optimisticLockerInterceptor = new OptimisticLockerInnerInterceptor();
            interceptor.addInnerInterceptor(optimisticLockerInterceptor);
            log.debug("配置并开启MybaitsPlus乐观锁");
        }

        // 防止全表更新与删除
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return interceptor;
    }
}
