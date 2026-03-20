package c.l.c.test.service;

import c.l.c.test.mq.config.OrderDelayQueue;
import c.l.c.test.mq.config.OrderQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * 测试服务
 *
 * @author lwh
 * @date 2021/12/6 17:15
 **/
@Slf4j
@Service
public class TestService {

    @StreamListener(OrderQueue.CONSUMER)
    public void orderQueueConsumer(Message<String> message) {
        log.info("接收到的消息：" + message.getPayload());
    }

    @StreamListener(OrderDelayQueue.CONSUMER)
    public void orderDelayQueueComsumer(Message<String> message) {
        log.info("接收到的消息：" + message.getPayload());
    }
}