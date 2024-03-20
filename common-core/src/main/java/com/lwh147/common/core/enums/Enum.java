package com.lwh147.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 数据库字段枚举类行为规范接口
 * <p>
 * 作为数据库表字段使用的枚举类型推荐按照该接口种描述的规范去实现枚举类
 * <p>
 * 如果是简单枚举类（只有枚举对象名称的枚举类），所有枚举必须添加注释对枚举释义
 *
 * @author lwh
 * @date 2023/7/7 14:57
 **/
public interface Enum {
    /**
     * 获取枚举值，即数据库里存储的值
     *
     * @return 枚举值
     * @apiNote {@link JsonValue} 可指定序列化时使用该方法结果作为枚举值，基于 Jackson
     * {@link EnumValue} 不能打在方法上，需要每个实现类自己在目标属性field上打上该注解
     **/
    @JsonValue
    String getValue();

    /**
     * 获取枚举名称，即值的中文描述
     *
     * @return 枚举名称
     **/
    String getName();

    /**
     * 推荐按 value-name 格式输出以提高日志和接口文档的可读性
     * <p>
     * {@link java.lang.Enum#toString()} 方法默认返回枚举的名字，可读性较差，实际使用中该方法比较鸡肋，基本上不会用到，但如果
     * 项目使用 Swagger，而枚举类型作为参数时 Swagger 会默认调用枚举对象的 toString() 方法在界面上展示
     *
     * @apiNote 作者本意是想通过该接口的默认方法为枚举类统一重写toString()方法，但由于语法限制无法实现{@code default toString()}，会报
     * 编译错误：Default method 'toString' overrides a member of 'java.lang.Object'
     * @see <a href="https://blog.csdn.net/Mr__fang/article/details/86158329">Java8 默认方法 default method</a>
     * @see <a href="https://blog.csdn.net/weixin_38308374/article/details/112440657">Java 8 新特性之接口默认方法和静态方法</a>
     **/
    String toString();
}