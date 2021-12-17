# common-mybatisplus-starter 参考文档

## 简介

Mybaits-Plus的启动类封装，主要封装了雪花算法Id生成器以及Mybatis-Plus的相关配置，如分页插件配置、乐观锁配置等

## 简单使用

添加Maven依赖

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-mybatisplus-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

SpringBoot程序启动类注解 `@SpringBootApplication` 增加 `com.lwh147.common` 基础扫描包路径即可

```java
@SpringBootApplication(scanBasePackages = {
        "com.lwh147.common",
        ...
})
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
```

具体配置请查看下面的 [配置项](#peizhixiang) 一节

> 需要手动添加@MapperScan指定扫描包路径或为Mapper接口打@Mapper注解

<div id="peizhixiang"/>

## 配置项

### 实体类的父类

为了简化实体类的编写，在本模块中设计了实体类的父类对表中的一些必须公共字段进行了封装，具体表格实体类只需要继承该父类后添加自己特有的属性即可，现对两个实体类父类的封装进行如下说明:

* BaseModel extends Model

继承自Model，包含主键、创建时间和修改时间三个字段

| 属性名 | 类型 | 数据库字段名 |
|-------|-------|-------|
| `id` | `Long` | `id` |
| `createTime` | `Date` | `create_time` |
| `updateTime` | `Date` | `update_time` |

* DataModel extends BaseModel

继承自BaseModel，增加了逻辑删除和版本控制两个字段

| 属性名 | 类型 | 数据库字段名 |
|-------|-------|-------|
| `deleted` | `Integer` | `deleted` |
| `version` | `Integer` | `version` |

> 实体类属性名 与 数据库表字段名 之间采用默认的 驼峰 与 下划线 映射方式

用户可根据需要选择继承两者

### 雪花算法ID生成器配置

配置项前缀 `snowflake`

| 配置项名称 | 类型 | 默认值 | 说明 |
|-------|-------|-------|-------|
| `enabled` | `Boolean` | `true` | 是否开启雪花算法ID生成 |
| `id-type` | `enum SnowflakeIdType` | `standalone` | 雪花算法ID生成类型，共有3种模式的雪花算法ID生成器，分别是单机模式（standalone）、集群模式（cluster）和用户指定模式（customized） |
| `data-center-id` | `Long` | - | 工作机器ID，仅在用户指定模式（customized）模式下有效 |
| `worker-id` | `Long` | - | 数据中心ID，仅在用户指定模式（customized）模式下有效 |

### MybatisPlus插件配置

配置前缀 `mybatis-plus`

| 配置项名称 | 类型 | 默认值 | 说明 |
|-------|-------|-------|-------|
| `enableOptimisticLocker` | `Boolean` | `true` | 是否开启乐观锁 |
| `page.enabled` | `Boolean` | `true` | 是否开启分页 |
| `page.dbType` | `enum DbType` | `mysql` | 数据库类型 |
| `page.enableOptimizeJoin` | `Boolean` | `true` | 是否开启左连接优化 |

## 待办

* 代码生成器
* SQL打印格式