# common-swagger-starter 参考文档

## 简介

本部分对Swagger进行了封装，引入jar包之后默认开启Swagger，支持在配置文件中进行开启或关闭控制，同时也支持对扫描包路径、匹配路径规则以及API信息等进行配置，但是目前仅支持基础配置，等待后续更新迭代

> 使用的Swagger版本为 Springfox-3.0.0

## 简单使用

引入jar包即可

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-swagger-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 配置项

配置前缀 `springfox.documentation`

| 配置项名称      | 类型          |  是否必填 | 备注 |
| -------------  | ------------  | -------- | --------------------------------------------- |
| enabled        | Bool          | 否       | 是否开启Swagger，默认 `true` |
| packages       | List\<String> | 否       | 扫描包路径，可以配置多个，默认全部扫描 |
| paths          | List\<String> | 否       | 路径匹配规则，可以配置多个，默认匹配所有 |
| excluded-paths | List\<String> | 否       | 排除的匹配规则，可配置多个，默认无 |
| version        | String        | 否       | API版本号，默认为 `1.0.0` |
| title          | String        | 否       | 页面简介标题，默认为 `${spring.appliction.name}` |
| description    | String        | 否       | 页面内容描述，默认为 `接口文档` |
| contact.name   | String        | 否       | 联系人姓名，默认为 `本页管理员` |
| contact.url    | String        | 否       | 联系人主页，默认为百度主页 |
| contact.email  | String        | 否       | 联系人邮箱，默认为空 |

## 待办

* 分组API配置支持
* Model的枚举属性展示方式优化
