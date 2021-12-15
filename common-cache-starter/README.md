# common-cache-starter 参考文档

## 简介

封装了项目中常用的缓存框架：JetCache、SpringDataRedis、Redission的自动配置

> 使用Lettuce作为基础的Redis客户端

## 简单使用

引入Maven依赖，配置文件中添加Redis连接配置即可默认开启三者：

```xml

<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-cache-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

配置文件：

```yml
spring:
  redis:
    host: ip
    port: 6379
    password: 123456
    timeout: 3000
```

## 配置项

SpringDataRedis完全使用原生配置项，按照官方文档进行配置即可

JetCache配置项前缀为 `jetcache` ，所有可配置项如下：

| 配置项名称                         |  类型         |  是否必填 | 备注 |
| -------------                     | ------------  | -------- | --------------------------------------------- |
| database                          | Integer       | 否       | 数据库，默认 `1` |
| local-limit                       | Integer       | 否       | 本地缓存数量限制，默认 `1024` |
| local-expired-in                  | Integer       | 否       | 本地缓存过期时间，单位秒，默认 `60` |
| remote-expired-in                 | Integer       | 否       | 远程缓存过期时间，单位秒，默认 `180` |
| stat-interval-minutes             | Integer       | 否       | 统计时间间隔，单位分钟，默认 `15` |
| refresh                           | Integer       | 否       | 缓存刷新时间，单位秒，默认 `60` |
| stop-refresh-after-last-access    | Integer       | 否       | 停止缓存刷新时间，单位秒，默认 `180` |

> 缓存刷新不会自动生效，需要手动进行配置，这里提供配置项只是为了方便后续维护修改刷新策略

## 待办

* LettuceClient 集群、Sentinel、连接池配置
* JetCache 方法注解扫描包路径配置
* JetCache 缓存刷新记录停止刷新时间的键重复问题
* Redission
