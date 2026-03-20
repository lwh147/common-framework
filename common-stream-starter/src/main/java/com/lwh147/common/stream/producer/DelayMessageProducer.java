package com.lwh147.common.stream.producer;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * 延迟消息生产者接口，各消息中间件的延时消息生产者须实现此接口统一发送接口
 *
 * @author lwh
 * @date 2025/07/17 10:53
 **/
public interface DelayMessageProducer {

    <T> boolean send(MessageChannel messageChannel, Message<T> message, long delay);

    boolean send(MessageChannel messageChannel, String payload, long delay);
}
