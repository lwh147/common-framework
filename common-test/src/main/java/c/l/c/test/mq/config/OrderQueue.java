package c.l.c.test.mq.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 测试订单消息队列
 *
 * @author lwh
 * @date 2025/08/04 19:49
 **/
public interface OrderQueue {
    String PRODUCER = "order-producer";
    String CONSUMER = "order-consumer";

    @Output(PRODUCER)
    MessageChannel producer();

    @Input(CONSUMER)
    SubscribableChannel consumer();
}
