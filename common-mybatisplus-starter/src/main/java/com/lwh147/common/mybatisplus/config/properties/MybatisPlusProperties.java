package com.lwh147.common.mybatisplus.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义MybatisPlus配置
 *
 * @author lwh
 * @date 2021/11/26 16:41
 **/
@Data
@ConfigurationProperties(prefix = "service")
public class MybatisPlusProperties {
    /**
     * 配置当前系统是否只有一个服务，默认为：true
     **/
    private Boolean single = true;
}
