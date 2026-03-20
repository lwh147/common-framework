package com.lwh147.common.stream.producer;

import com.lwh147.common.stream.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Rabbit延迟消息生产者
 *
 * @author lwh
 * @date 2025/07/17 10:56
 **/
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.cloud.stream.default-binder", havingValue = "rabbit")
public class RabbitDelayMessageProducer implements DelayMessageProducer {

    @PostConstruct
    public void postConstruct() {
        log.info("Rabbit delay message producer constructed");
    }

    @Override
    public <T> boolean send(MessageChannel messageChannel, Message<T> message, long delay) {
        message.getHeaders().put(MessageConstant.RABBIT_DELAY_MESSAGE_HEADER_KEY, delay);
        return messageChannel.send(message);
    }

    @Override
    public boolean send(MessageChannel messageChannel, String payload, long delay) {
        return messageChannel.send(MessageBuilder.withPayload(payload)
                .setHeader(MessageConstant.RABBIT_DELAY_MESSAGE_HEADER_KEY, delay).build());
    }
}
