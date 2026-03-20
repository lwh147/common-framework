package com.lwh147.common.stream.autoconfigure;

import com.lwh147.common.stream.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.rabbit.config.RabbitMessageChannelBinderConfiguration;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitBindingProperties;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitExtendedBindingProperties;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ延迟绑定自动配置类
 * <p>
 * 在spring-cloud-stream中使用rabbitmq发送延时消息时，需要针对rabbitmq单独配置个性化绑定参数，违背了stream想要抽象封装统一各
 * 消息中间件使用逻辑差异的目标，本配置类根据约定好的信道、绑定目标等命名规则，自动识别判断延迟类型的信道绑定自动配置rabbitmq
 * 个性化参数，使使用者可以在延迟消息发送方面做到各消息中间件无感切换
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.cloud.stream.default-binder", havingValue = "rabbit")
@AutoConfigureBefore(RabbitMessageChannelBinderConfiguration.class)
@EnableConfigurationProperties(RabbitExtendedBindingProperties.class)
public class RabbitDelayBindingAutoConfiguration {
    @Resource
    private BindingService bindingService;
    @Resource
    private RabbitExtendedBindingProperties rabbitExtendedBindingProperties;

    @PostConstruct
    public void updateBinding() {
        log.info("Rabbit delay binding auto configuration triggered");
        Map<String, BindingProperties> streamBindings = bindingService.getBindingServiceProperties().getBindings();
        Map<String, RabbitBindingProperties> rabbitBindings = new HashMap<>(rabbitExtendedBindingProperties.getBindings());
        for (Map.Entry<String, BindingProperties> streamBinding : streamBindings.entrySet()) {
            if (streamBinding.getValue().getDestination().toUpperCase().contains(MessageConstant.DELAY_DESTINATION_FLAG)) {
                RabbitBindingProperties rabbitBindingProperties = rabbitBindings.get(streamBinding.getKey());
                if (rabbitBindingProperties == null) {
                    rabbitBindingProperties = new RabbitBindingProperties();
                }
                if (streamBinding.getKey().toUpperCase().contains(MessageConstant.OUTPUT_CHANNEL_FLAG)) {
                    rabbitBindingProperties.getProducer().setDelayedExchange(true);
                } else if (streamBinding.getKey().toUpperCase().contains(MessageConstant.INPUT_CHANNEL_FLAG)) {
                    rabbitBindingProperties.getConsumer().setDelayedExchange(true);
                } else {
                    rabbitBindingProperties.getConsumer().setDelayedExchange(true);
                    rabbitBindingProperties.getProducer().setDelayedExchange(true);
                }
                rabbitBindings.put(streamBinding.getKey(), rabbitBindingProperties);
                log.info("Rabbit stream binding [{} -> {}] configured as delayed exchange", streamBinding.getKey(),
                        streamBinding.getValue().getDestination());
            }
        }
        rabbitExtendedBindingProperties.setBindings(rabbitBindings);
    }
}
