package c.l.c.test.mq.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * 触发信道绑定
 *
 * @author lwh
 * @date 2025/01/23 09:13
 **/
@EnableBinding({
        OrderDelayQueue.class,
        OrderQueue.class
})
public class StreamConfig {
}
