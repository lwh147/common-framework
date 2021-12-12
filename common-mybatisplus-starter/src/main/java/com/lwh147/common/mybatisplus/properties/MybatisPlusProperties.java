package com.lwh147.common.mybatisplus.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.EnumMap;

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
     * 是否开启乐观锁
     **/
    private Boolean optimisticLocker = true;

    /**
     * 分页配置
     **/
    @Data
    public static class Page {
        /**
         * 是否开启分页
         **/
        private Boolean enabled = true;
        /**
         * 数据库类型
         **/
        // private EnumMap<DbType, String> dbType = new EnumMap<DbType, String>();
    }
}
