package c.l.c.test.controller;

import c.l.c.test.api.TestApi;
import c.l.c.test.exception.BusinessExceptionEnum;
import c.l.c.test.mq.config.OrderDelayQueue;
import c.l.c.test.mq.config.OrderQueue;
import c.l.c.test.pojo.query.UserQuery;
import com.lwh147.common.core.enums.CommonSortColumnEnum;
import com.lwh147.common.core.enums.SortOrderEnum;
import com.lwh147.common.stream.producer.DelayMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试控制器
 *
 * @author lwh
 * @date 2021/11/15 10:19
 **/
@Slf4j
@RestController
public class TestController implements TestApi {
    @Resource
    private DelayMessageProducer delayMessageProducer;
    @Resource
    private OrderDelayQueue orderDelayQueue;
    @Resource
    private OrderQueue orderQueue;

    @Override
    public void test() {
        throw new NullPointerException("test");
    }

    @Override
    public void test1(UserQuery query) {
        int i = 1 / 0;
    }

    @Override
    public void test2(SortOrderEnum order, CommonSortColumnEnum sortColumnEnum) {
        throw BusinessExceptionEnum.BUSINESS_ERROR.toException();
    }

    @Override
    public void test3() {
        delayMessageProducer.send(orderDelayQueue.producer(), "测试封装延时消息", 5000);
    }

    @Override
    public void test4() {
        orderQueue.producer().send(MessageBuilder.withPayload("测试i订单消息LL").build());
    }
}