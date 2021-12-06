# common-framework 参考文档

## 简介

本框架是一个能够加速开发基于SpringBoot的应用的通用基础开发框架

本着 “约定优先于配置”
的Spring核心思想，本框架将项目开发过程中常用的一些依赖库（如Swagger、Redis、消息中间件、统一异常、统一响应等）的配置过程各自封装成为starter，使得可以在不进行任何其它配置的情况下只需要引入对应模块就可以使用默认约定好的配置使用该模块功能，非常方便高效

### 项目框架

```text
common-framework ----------------------------- 基础开发框架根模块(root)
├─ common-core --------------------------------- 开发框架公共核心组件库
├─ common-util --------------------------------- 开发框架工具组件库
├─ common-cache-starter ------------------------ cache配置starter
├─ common-mybatisplus-starter ------------------ MybatisPlus配置starter
├─ common-stream-starter ----------------------- 消息中间件配置starter
├─ common-swagger-starter ---------------------- Swagger配置starter
├─ common-web-starter -------------------------- 开发框架Web配置starter
├─ common-test --------------------------------- 开发框架测试模块
```

### 技术选型

```text
JDK ---------------------- 1.8
编码 --------------------- UTF-8
交互数据格式 -------------- JSON
SpringBoot --------------- 2.3.2.RELEASE
SpringCloud -------------- Hoxton.SR9
注册中心 ------------------ Nacos 2.2.6.RELEASE
配置中心 ------------------ Nacos 2.2.6.RELEASE
数据库ORM框架 ------------- MybatisPlus 3.3.1
缓存 --------------------- JetCahce 2.6.0
分布式锁 ----------------- Redission 3.12.5
接口文档 ----------------- Swagger 3.0.0
消息中间件 --------------- Stream Horsham.SR9
数据库 ------------------- Mysql 5.7
JDBC -------------------- 8.0.16
工具集 ------------------- Hutool 5.7.14
```

> 本框架默认字符编码为UTF-8（文件相关的请求和响应除外），支持的数据交互格式为JSON，即请求和响应统一使用JSON格式

## 简单使用

你的项目中需要哪个模块的内容就引入哪个模块的jar包即可，开箱即用，如果需要进行相关配置，请前往对应模块的参考文档查看

common-framework

* [common-core](./common-core/README.md)
* [common-util](./common-util/README.md)
* [common-cache-starter](./common-cache-starter/README.md)
* [common-mybatisplus-starter](./common-mybatisplus-starter/README.md)
* [common-stream-starter](./common-stream-starter/README.md)
* [common-swagger-starter](./common-swagger-starter/README.md)
* [common-web-starter](./common-web-starter/README.md)
* [common-test](./common-test/README.md)

## 开发规约

阿里巴巴Java开发手册-v1.7.0-嵩山版

## 待办
