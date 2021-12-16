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

推荐使用JetCache的方法注解进行方法缓存，使用RedisTemplate进行手动缓存操作

## 配置项

SpringDataRedis按照[官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.data)进行配置即可

JetCache和Redisson均共享SpringDataRedis关于Redis连接地址、端口、密码、连接池等相关配置项（ `spring.redis.*` ）

除此之外，针对JetCache提供了一些额外的配置项以允许用户自定义其功能，JetCache配置项前缀为 `jetcache` ，所有可配置项如下表所示：

| 配置项名称 | 类型 | 默认值 | 说明 |
|-------|-------|-------|-------|
| `database` | `Integer` | `1` | 数据库 |
| `local-limit` | `Integer` | `1024` | 本地缓存数量限制 |
| `local-expired-in` | `Long` | `60` | 本地缓存过期时间，单位秒 |
| `remote-expired-in` | `Long` | `180` | 远程缓存过期时间，单位秒 |
| `stat-interval-minutes` | `Integer` | `15` | 统计时间间隔，单位分钟 |
| `refresh` | `Long` | `60` | 缓存刷新时间，单位秒 |
| `stop-refresh-after-last-access` | `Long` | `180` | 停止缓存刷新时间，单位秒 |

> 缓存刷新相关配置不会自动生效，需要手动进行配置，这里提供配置项只是为了方便后续维护修改刷新策略

Redisson目前没有进行任何定制化配置，使用的就是自动配置生成的默认 `RedissonClient`

## 待办

* 由MybatisPlus引入时是否开启问题
* LettuceClient 集群、Sentinel、连接池配置
* JetCache 方法注解扫描包路径配置
* JetCache 缓存刷新记录停止刷新时间的键重复问题
