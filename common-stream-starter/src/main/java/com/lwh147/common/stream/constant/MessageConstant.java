package com.lwh147.common.stream.constant;

import com.alibaba.cloud.stream.binder.rocketmq.constant.RocketMQConst;

/**
 * 消息中间件相关常量类
 *
 * @author lwh
 * @date 2021/11/17 14:57
 **/
public class MessageConstant {
    /**
     * 延时类型信道绑定目标命名关键字
     **/
    public static final String DELAY_DESTINATION_FLAG = "DELAY";
    /**
     * 输入信道命名关键字
     **/
    public static final String INPUT_CHANNEL_FLAG = "CONSUMER";
    /**
     * 输出信道命名关键字
     **/
    public static final String OUTPUT_CHANNEL_FLAG = "PRODUCER";

    /**
     * 各消息中间件的延时消息头部标识
     **/
    public static final String ROCKET_DELAY_MESSAGE_HEADER_KEY = RocketMQConst.PROPERTY_DELAY_TIME_LEVEL;
    public static final String RABBIT_DELAY_MESSAGE_HEADER_KEY = "x-delay";
    public static final String KAFKA_DELAY_MESSAGE_HEADER_KEY = "delay";

    private MessageConstant() {
        throw new IllegalStateException("Constant class");
    }
}