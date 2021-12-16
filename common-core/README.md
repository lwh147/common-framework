# common-core 参考文档

## 简介

该模块主要包含常用常量封装类、统一响应、统一应答、统一异常以及通用查询条件的封装类

## 简单使用

如不需要统一异常等配置，只需要使用到本模块中的封装类，只引入Jar包即可

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 配置项

无

## 待办

* 由于枚举类的特殊性（编程能力有限），方法 fromValue() 和 exist() 目前需要在枚举类中手动添加实现
* 枚举类型作为请求参数进行绑定时不能自动映射（因为请求体采用JSON反序列化的方式绑定，请求参数是从Map<String, Object>中取值然后给Bean属性赋值的方式绑定的，在这个过程中Object对象不能转换为枚举类型所以出错）
