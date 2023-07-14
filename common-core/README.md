# common-core 参考文档

## 简介

该模块主要包含常用常量工具封装类、统一响应、统一应答、统一异常以及通用查询条件的封装类

## 简单使用

添加Maven依赖

```xml
<dependency>
    <groupId>com.lwh147</groupId>
    <artifactId>common-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 配置项

无

## 代码风格建议

* 推荐枚举类创建时默认添加比较常用的 `from()` 和 `exist()`
  方法和实现（由于 [枚举类的特殊性](https://stackoverflow.com/questions/19433364/why-cant-a-class-extend-an-enum) ，这两个方法只能手动添加实现）

## 待办

* 枚举类型作为请求参数进行绑定时不能自动映射（因为请求体采用JSON反序列化的方式绑定，请求参数是从Map<String, Object>中取值然后给Bean属性赋值的方式绑定的，在这个过程中Object对象不能转换为枚举类型所以出错）