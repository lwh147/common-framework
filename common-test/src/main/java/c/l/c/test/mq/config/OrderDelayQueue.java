package c.l.c.test.mq.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 测试延时消息队列
 *
 * @author lwh
 * @date 2025/08/04 19:49
 **/
public interface OrderDelayQueue {
    String PRODUCER = "order-delay-producer";
    String CONSUMER = "order-delay-consumer";

    @Output(PRODUCER)
    MessageChannel producer();

    @Input(CONSUMER)
    SubscribableChannel consumer();
}
