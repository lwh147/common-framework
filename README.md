# common-framework 参考文档

## 简介

本框架是一个加速SpringBoot应用程序开发的通用开发框架

本着 **约定优先于配置** 的Spring核心思想，本框架将项目开发过程中常用的一些依赖库（如Swagger、Redis缓存框架、消息中间件、统一异常、统一响应等）的配置过程分别封装为 `starter`
，使得可以在不进行任何其它配置的情况下只需要引入对应模块就可以以默认约定好的配置使用该模块功能

### 项目框架

项目采用Maven多模块的组织结构，各模块内容简介如下：

```text
common-framework ----------------------------- 通用开发框架根模块(root)
├─ common-core --------------------------------- 开发框架公共核心组件库
├─ common-util --------------------------------- 开发框架工具组件库
├─ common-cache-starter ------------------------ 缓存框架配置starter
├─ common-mybatisplus-starter ------------------ MybatisPlus配置starter
├─ common-stream-starter ----------------------- 消息中间件配置starter
├─ common-swagger-starter ---------------------- Swagger配置starter
├─ common-web-starter -------------------------- Web配置starter
├─ common-test --------------------------------- 测试模块
```

### 技术选型

首先，本项目基于 `Jdk-1.8` 进行开发，字符编码均采用 `UTF-8` ，规定默认的数据交互格式为 `JSON` （上传文件等除外），JSON工具库默认使用 `Jackson`

> 目前不支持配置JSON工具为FastJson或其他

下面是本项目的所有依赖项及版本列表：

```text
spring-boot-dependencies ---------------------- 2.3.2.RELEASE
spring-cloud-dependencies --------------------- Hoxton.SR9
spring-cloud-alibaba-dependencies ------------- 2.2.6.RELEASE
mysql-connector-java -------------------------- 8.0.16
mybaits-plus-boot-starter --------------------- 3.3.1
p6spy ----------------------------------------- 3.9.1
lombok ---------------------------------------- 1.18.12
logback-classic ------------------------------- 1.2.3
sprignfox-swagger2 ---------------------------- 2.9.2
sprignfox-swagger-ui -------------------------- 2.9.2
swagger-annotations --------------------------- 1.5.24
swagger-models -------------------------------- 1.5.24
redisson-spring-boot-starter ------------------ 3.12.5
jetcache-starter-redis-lettuce ---------------- 2.6.0
reflections ----------------------------------- 0.9.11
hutool-all ------------------------------------ 5.7.14
```

## 简单使用

你的项目中需要哪个模块的功能就引入哪个模块的jar包即可，开箱即用，如果需要进行相关配置，请前往对应模块的参考文档查看

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
