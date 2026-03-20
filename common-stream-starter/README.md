# common-stream-starter 参考文档

## 简介

该模块旨在通过 `spring-cloud-stream` 集成常用的 `rabbit` 、 `kafka` 、 `rocket` 消息中间件，对外提供统一的消息绑定、生产、消费接口，
将具体的消息中间件解耦，当项目要求使用的消息中间件发生变化时只需更改配置切换使用的中间件类型即可，业务服务模块无需重新对接或改造，
就算使用新的消息中间件也只需改造该模块进行适配，不需要各个业务服务模块都进行改造

## 简单使用

## 配置项

| 配置项名称                                | 类型       | 默认值 | 说明                                        |
|--------------------------------------|----------|-----|-------------------------------------------|
| `spring.cloud.stream.default-binder` | `String` | `-` | 指定使用的消息中间件： `rabbit` 、 `kafka` 、 `rocket` |

## 规范

* （必要）延时信道绑定目标必须包含`delay`关键字，否则针对延时消息的自动配置逻辑会失效
* （推荐）使用短横线命名法（kebab-case），因为RocketMQ不支持`.`
* （推荐）绑定目标`destination`名称以`*-queue`结尾
* （推荐）通道`channel`名称以`*-consumer`或`*-producer`结尾
* （推荐）分组名称以绑定目标为前缀，以`*-group`结尾

举例：

```yml
spring:
  cloud:
    stream:
      bindings:
        order-producer:
          destination: order-queue
        order-consumer:
          destination: order-queue
          group: order-queue-default-group
        order-delay-producer:
          destination: order-delay-queue
        order-delay-consumer:
          destination: order-delay-queue
          group: order-delay-queue-default-group
```

## 待办

* 待实现基础功能
* 消除三个消息中间件之间的差异化配置项（kafka延迟消息处理）
* 基础消息结构、消费者、生产者以及消息重试、死信队列、异常处理逻辑封装
