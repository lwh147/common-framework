# common-framework 参考文档

## 简介

本框架是一个加速SpringBoot应用程序开发的通用开发框架

本着 **约定优先于配置** 的Spring核心思想，本框架将项目开发过程中常用的一些依赖库（如Swagger、Redis缓存框架、消息中间件等）以及一些必备功能（如统一异常、统一日志等）分别封装为 `starter`
，使得在不进行任何其它多余配置的情况下只需要引入对应模块的Maven依赖就可以以默认约定好的配置使用该模块功能

### 项目框架

项目采用Maven多模块的组织结构，各模块内容如下：

```text
common-framework ------------------------------- 通用开发框架根模块(root)
├─ common-cache-starter ------------------------ 常用缓存框架自动配置模块
├─ common-core --------------------------------- 开发框架核心模块
├─ common-model -------------------------------- 开发框架基础模型定义模块
├─ common-mybatisplus-starter ------------------ MybatisPlus自动配置模块
├─ common-stream-starter ----------------------- 消息中间件自动配置模块
├─ common-swagger-starter ---------------------- Swagger自动配置模块
├─ common-test --------------------------------- 框架测试模块
├─ common-util --------------------------------- 框架工具集模块
├─ common-web-starter -------------------------- Web自动配置模块
```

## 简单使用

根据你需要的功能，添加对应模块的Maven依赖即可使用（或需要进行一点点必须的配置），至于具体的模块功能说明和配置说明，请前往对应模块的参考文档查看

* [common-cache-starter](./common-cache-starter/README.md)
* [common-core](./common-core/README.md)
* [common-model](./common-model/README.md)
* [common-mybatisplus-starter](./common-mybatisplus-starter/README.md)
* [common-stream-starter](./common-stream-starter/README.md)
* [common-swagger-starter](./common-swagger-starter/README.md)
* [common-test](./common-test/README.md)
* [common-util](./common-util/README.md)
* [common-web-starter](./common-web-starter/README.md)

### 技术选型

基于 `Jdk-1.8` 进行开发，字符编码均采用 `UTF-8` ，规定默认的数据交互格式为 `JSON` （上传文件等除外），JSON工具库默认使用 `Jackson`

> 目前不支持配置JSON工具为Fastjson或其他

下面是本项目的所有依赖项及版本列表：

```text
spring-boot-dependencies ---------------------- 2.3.12.RELEASE
spring-cloud-dependencies --------------------- Hoxton.SR12
spring-cloud-alibaba-dependencies ------------- 2.2.10-RC1
mysql-connector-java -------------------------- 8.0.28
mybaits-plus-boot-starter --------------------- 3.4.3.4
mybaits-plus-generator ------------------------ 3.4.1
druid-spring-boot-starter --------------------- 1.2.8
lombok ---------------------------------------- 1.18.24
logback-classic ------------------------------- 1.2.11
sprignfox-swagger2 ---------------------------- 2.9.2
sprignfox-swagger-ui -------------------------- 2.9.2
swagger-annotations --------------------------- 1.5.24
swagger-models -------------------------------- 1.5.24
redisson-spring-boot-starter ------------------ 3.12.5
jetcache-starter-redis-lettuce ---------------- 2.6.7
reflections ----------------------------------- 0.10.2
hutool-all ------------------------------------ 5.7.22
```

## 开发规约

阿里巴巴Java开发手册-v1.7.0-嵩山版

## 待办

* 项目模块结构优化，继续细分基础模型、工具、核心、启动模块
* 统一异常优化
* 包结构及命名优化