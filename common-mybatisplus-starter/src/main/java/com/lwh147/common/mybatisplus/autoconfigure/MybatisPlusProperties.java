package com.lwh147.common.mybatisplus.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义 MybatisPlus 配置项
 *
 * @author lwh
 * @date 2021/11/26 16:41
 **/
@Data
@ConfigurationProperties(prefix = "snowflake.data-center")
public class MybatisPlusProperties {
    /**
     * 是否开启分布式ID生成，默认为：true
     **/
    private Boolean enabled = true;
}
