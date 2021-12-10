# common-web-starter 参考文档

## 简介

该模块基于common-core模块的封装类完成统一异常处理的配置，另外还有统一日志打印、Jackson全局序列化策略的配置等功能

## 简单使用

引入jar包，SpringBoot程序启动类注解 `@SpringBootApplication` 增加 `com.lwh147.common` 基础扫描包路径即可

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-web-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 配置项

无

## 待办

* 响应日志记录器切面扫描包路径
* 异常转换器扫描包路径
