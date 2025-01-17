# common-web-starter 参考文档

## 简介

该模块基于common-core模块的封装类完成统一异常处理的配置，另外还有统一日志打印、Jackson全局序列化策略的配置等功能

## 简单使用

添加Maven依赖

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-web-starter</artifactId>
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

## 配置项

配置前缀 `web`

| 配置项名称 | 类型        | 默认值    | 说明                                                 |
| --------- |-----------|--------|----------------------------------------------------|
| `enable-banner-print` | `Boolean` | `true` | 是否打印Banner                                         |
| `request-params-and-body-print-length` | `Integer` | `-1`   | 请求及响应参数内容打印字符长度限制：< 0 不限制，= 0 不打印，> 0 限制，默认 -1 不限制 |
| `global-exception-handler.enabled` | `Boolean` | `true` | 是否开启全局异常处理                                         |
| `global-exception-handler.converter-scan-custom-package` | `String`  | -      | 自定义的异常转换器扫描包路径                                     |

## 待办