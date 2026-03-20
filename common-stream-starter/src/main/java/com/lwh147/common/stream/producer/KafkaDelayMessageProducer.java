package com.lwh147.common.stream.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Kafka延迟消息生产者
 *
 * @author lwh
 * @apiNote 暂未实现
 * @date 2025/07/17 10:56
 **/
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.cloud.stream.default-binder", havingValue = "kafka")
public class KafkaDelayMessageProducer implements DelayMessageProducer {

    @PostConstruct
    public void postConstruct() {
        log.warn("KafKa delay message producer constructed, but temporarily not supported!");
    }

    @Override
    public <T> boolean send(MessageChannel messageChannel, Message<T> message, long delay) {
        throw new IllegalStateException("Delayed message is temporarily not supported when using Kafka!");
    }

    @Override
    public boolean send(MessageChannel messageChannel, String payload, long delay) {
        throw new IllegalStateException("Delayed message is temporarily not supported when using Kafka!");
    }
}