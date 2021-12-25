package com.lwh147.common.mybatisplus.properties;

import com.lwh147.common.mybatisplus.properties.enums.DbType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Mybatis-plus 配置类
 *
 * @author lwh
 * @date 2021/12/12 23:07
 **/
@Data
@ConfigurationProperties(prefix = "mybatis-plus")
public class MybatisPlusProperties {
    /**
     * 是否开启乐观锁，默认为 {@code true}
     **/
    private Boolean enableOptimisticLocker = true;
    /**
     * 是否防止全表更新与删除，默认为 {@code true}
     **/
    private Boolean enableBlockAttackCheck = true;
    /**
     * 是否拦截垃圾sql，因过滤规则比较严格，默认为 {@code false}
     **/
    private Boolean enableIllegalSqlCheck = false;
    /**
     * 分页配置
     **/
    @NestedConfigurationProperty
    private Page page = new Page();

    /**
     * 分页配置封装类
     **/
    @Data
    public static class Page {
        /**
         * 是否开启分页，默认为 {@code true}
         **/
        private Boolean enabled = true;
        /**
         * 数据库类型，默认为 {@link DbType#MYSQL}
         **/
        private DbType dbType = DbType.MYSQL;
        /**
         * 是否开启左连接优化，默认为 {@code true}
         **/
        private Boolean enableOptimizeJoin = true;
    }
}
