# common-core 参考文档

## 简介

该模块是本框架的核心模块，主要整合引入了框架基本模块（common-model和common-util）并添加了一些其它各模块通用的依赖，除此之外还定义了请求上下文、自定义异常、分页查询条件、分页数据、统一响应等核心数据模型

## 简单使用

添加Maven依赖

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 配置项

无

## 待办事项

* 异常封装处理逻辑优化
* 上下文存储细化：spring、request、biz