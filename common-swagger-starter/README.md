# common-swagger-starter 参考文档

## 简介

本部分对Swagger进行了封装，添加依赖之后默认开启Swagger，支持在配置文件中进行开启或关闭控制，同时也支持对扫描包路径、匹配路径规则以及API信息等进行配置，但是目前仅支持基础配置，等待后续更新迭代

新增了Knife4j的支持，共用Swagger的API相关配置

> 使用的Swagger版本为 `2.9.2` ，其中 annotations 和 models 模块使用 `1.5.24` 版本， `1.5.20` 及之前的版本存在默认空串强转错误等影响使用体验的bug

> 使用的Knife4j版本为 `2.0.5` ，适配 Swagger版本为 `2.9.2`

## 简单使用

添加Maven依赖

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-swagger-starter</artifactId>
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

<div id="peizhixiang"></div>

## 配置项

配置前缀 `swagger`

| 配置项名称 | 类型 | 默认值 | 说明 |
| --------- | ---- | ----- | ---- |
| `enabled` | `Boolean` | `true` | 是否开启Swagger |
| `packages` | `List<String>` | - | 扫描包路径，可以配置多个 |
| `paths` | `List<String>` | - | 路径匹配规则，可以配置多个 |
| `excluded-paths` | `List<String>` | `^/error$` | 排除的匹配规则，可配置多个 |
| `version` | `String` | `1.0.0` | API版本号 |
| `title` | `String` | `${spring.appliction.name:app_name}` | 页面简介标题 |
| `description` | `String` | `接口文档` | 页面内容描述 |
| `contact.name` | `String` | `管理员` | 联系人姓名 |
| `contact.url` | `String` | `www.baidu.com` | 联系人主页 |
| `contact.email` | `String` | - | 联系人邮箱 |

## 待办

* 分组API配置支持
* Swagger和Knife4j开关区分处理