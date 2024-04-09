package com.lwh147.common.core.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 简单的 value-name 数据库通用枚举类行为规范接口，依赖 MybatisPlus 实现
 * <p>
 * 继承 MybatisPlus 的 {@link IEnum}接口，实现该接口的枚举类无需再进行 {@code @EnumValue} 注解
 *
 * @param <T> 需为可序列化类型，因为有序列化存储到数据库的需求
 * @author lwh
 * @date 2023/7/7 14:57
 **/
public interface ValueNameEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 获取枚举值，即枚举code（数据库里存储的值）
     *
     * @return 枚举值
     **/
    @Override
    T getValue();

    /**
     * 获取枚举名称，即值的中文描述
     *
     * @return 枚举名称
     **/
    String getName();

    /**
     * 推荐按 value-name 格式输出
     * <p>
     * {@link Enum#toString()} 方法默认返回枚举的名字，可读性较差，实际使用中该方法比较鸡肋，基本上不会用到，但如果项目使用
     * Swagger，而枚举类型作为参数时 Swagger 会默认调用枚举对象的 toString() 方法在界面上展示
     * {@code springfox.documentation.schema.Enums#getEnumValues} （其实 Jackson 的 {@code @JsonValue} 方法注解优先级更高）
     * <p>
     * 作者本意是想通过接口的默认方法直接为枚举类统一重写 toString() 方法，但由于语法限制无法实现
     * {@code default toString()}，会报编译错误：Default method 'toString' overrides a member of 'java.lang.Object'
     * <p>
     * 类似的，由于枚举类的特殊性也不能通过接口提供统一的通用方法 {@link ValueNameEnum#from} 实现
     * <p>
     * 退而求其次，该接口提供了需要指定类型的静态 {@link ValueNameEnum#from} 和 {@link ValueNameEnum#toString} 方法
     *
     * @see <a href="https://blog.csdn.net/Mr__fang/article/details/86158329">Java8 默认方法 default method</a>
     * @see <a href="https://blog.csdn.net/weixin_38308374/article/details/112440657">Java 8 新特性之接口默认方法和静态方法</a>
     **/
    @Override
    String toString();

    /**
     * 根据枚举值寻找枚举对象
     *
     * @param <T> 实现了此接口的枚举类型
     * @return 找到的枚举对象，没找到返回 {@code null}
     **/
    static <T extends Enum<T> & ValueNameEnum<? extends Serializable>> T from(Class<T> enumType, String value) {
        for (T e : enumType.getEnumConstants()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." + value);
    }

    /**
     * 泛型toString
     *
     * @param <T> 实现了此接口的枚举类型
     **/
    static <T extends Enum<T> & ValueNameEnum<? extends Serializable>> String toString(T enumObj) {
        return enumObj.getValue() + "-" + enumObj.getName();
    }
}