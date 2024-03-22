package com.lwh147.common.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lwh147.common.core.enums.serialization.ValueNameEnumDeserializer;
import com.lwh147.common.core.enums.serialization.ValueNameEnumSerializer;

/**
 * 简单的value-name枚举类行为规范接口
 * <p>
 * 作为数据库表字段使用的枚举类型推荐按照该接口中描述的规范去实现枚举类
 * <p>
 * 如果是简单枚举类（只有枚举对象名称的枚举类），所有枚举必须添加注释对枚举释义
 *
 * @author lwh
 * @date 2023/7/7 14:57
 **/
@JsonSerialize(using = ValueNameEnumSerializer.class)
@JsonDeserialize(using = ValueNameEnumDeserializer.class)
public interface ValueNameEnum {
    /**
     * 根据枚举值寻找枚举对象
     *
     * @param <T> 实现了此接口的枚举类型
     * @return 找到的枚举对象，没找到返回 {@code null}
     **/
    static <T extends Enum<T> & ValueNameEnum> T from(Class<T> enumType, String value) {
        for (T e : enumType.getEnumConstants()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 获取枚举名称，即值的中文描述
     *
     * @return 枚举名称
     **/
    String getName();

    /**
     * 判断枚举值是否存在
     *
     * @param <T> 实现了此接口的枚举类型
     * @return 枚举值是否存在
     **/
    static <T extends Enum<T> & ValueNameEnum> boolean exist(Class<T> enumType, String value) {
        return from(enumType, value) != null;
    }

    /**
     * 泛型toString
     *
     * @param <T> 实现了此接口的枚举类型
     **/
    static <T extends Enum<T> & ValueNameEnum> String toString(T enumObj) {
        return enumObj.getValue() + "-" + enumObj.getName();
    }

    /**
     * 获取枚举值，即枚举code或数据库里存储的值
     *
     * @return 枚举值
     **/
    String getValue();

    /**
     * 强烈推荐按 value-name 格式输出以提高日志和接口文档的可读性
     * <p>
     * {@link Enum#toString()} 方法默认返回枚举的名字，可读性较差，实际使用中该方法比较鸡肋，基本上不会用到，但如果
     * 项目使用 Swagger，而枚举类型作为参数时 Swagger 会默认调用枚举对象的 toString() 方法在界面上展示
     *
     * @apiNote 作者本意是想通过该接口的默认方法为枚举类统一重写 toString() 方法，但由于语法限制无法实现
     * {@code default toString()}，会报编译错误：Default method 'toString' overrides a member of 'java.lang.Object'
     * <p>
     * 类似的，由于枚举类的特殊性，静态方法 from 和 exist 也不能提供统一的通用的实现
     * <p>
     * 退而求其次，接口提供了需要指定类型的静态 {@link ValueNameEnum#from}，{@link ValueNameEnum#exist} 和
     * {@link ValueNameEnum#toString(Enum)} 方法，如果不想在实现类中自己写也可以使用这些方法，但 from 和 toString 是强烈推荐
     * 实现的
     * @see <a href="https://blog.csdn.net/Mr__fang/article/details/86158329">Java8 默认方法 default method</a>
     * @see <a href="https://blog.csdn.net/weixin_38308374/article/details/112440657">Java 8 新特性之接口默认方法和静态方法</a>
     **/
    String toString();
}