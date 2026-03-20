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
 * Rocket延迟消息生产者
 *
 * @author lwh
 * @date 2025/07/17 10:56
 **/
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.cloud.stream.default-binder", havingValue = "rocketmq")
public class RocketDelayMessageProducer implements DelayMessageProducer {

    @PostConstruct
    public void postConstruct() {
        log.info("Rocket delay message producer constructed");
    }

    @Override
    public <T> boolean send(MessageChannel messageChannel, Message<T> message, long delay) {
        message.getHeaders().put(MessageConstant.ROCKET_DELAY_MESSAGE_HEADER_KEY, delayToLevel(delay));
        return messageChannel.send(message);
    }

    @Override
    public boolean send(MessageChannel messageChannel, String payload, long delay) {
        return messageChannel.send(MessageBuilder.withPayload(payload)
                .setHeader(MessageConstant.ROCKET_DELAY_MESSAGE_HEADER_KEY, delayToLevel(delay)).build());
    }

    /**
     * rocketmq只支持固定18个等级的延迟消息，将具体时间转换为等级
     **/
    private int delayToLevel(long delay) {
        if (delay <= 1000L) {
            return 1;
        }
        if (delay <= 5000L) {
            return 2;
        }
        if (delay <= 10000L) {
            return 3;
        }
        if (delay <= 30000L) {
            return 4;
        }
        if (delay <= 60000L) {
            return 5;
        }
        if (delay <= 120000L) {
            return 6;
        }
        if (delay <= 180000L) {
            return 7;
        }
        if (delay <= 240000L) {
            return 8;
        }
        if (delay <= 300000L) {
            return 9;
        }
        if (delay <= 360000L) {
            return 10;
        }
        if (delay <= 420000L) {
            return 11;
        }
        if (delay <= 480000L) {
            return 12;
        }
        if (delay <= 540000L) {
            return 13;
        }
        if (delay <= 600000L) {
            return 14;
        }
        if (delay <= 1200000L) {
            return 15;
        }
        if (delay <= 1800000L) {
            return 16;
        }
        if (delay <= 3600000L) {
            return 17;
        }
        return 18;
    }
}
