package com.lwh147.common.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 示例枚举类
 * <p>
 * 简单枚举类（只有枚举对象名称的枚举类）的所有枚举必须添加注释对枚举释义
 *
 * @author lwh
 * @date 2021/10/26 16:45
 **/
public enum SampleEnum implements Enum {
    /**
     * 枚举列表
     **/
    SAMPLE1("1", "这是示例1"),
    SAMPLE2("2", "这是示例2"),
    ;

    /**
     * 枚举值，可视为枚举的唯一识别码
     * <p>
     * {@link EnumValue} 可指定数据入库时使用该属性作为枚举值，基于 MybatisPlus
     * <p>
     * {@link JsonValue} 可指定序列化时使用该属性作为枚举值，基于 Jackson
     **/
    @EnumValue
    @JsonValue
    private final String value;
    /**
     * 枚举值名称，可视为对枚举值的简单描述
     **/
    private final String name;

    /**
     * 构造方法，默认私有
     **/
    SampleEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据枚举值寻找枚举对象
     * <p>
     * {@link JsonCreator} 可指定使用此方法进行反序列化，基于Jackson
     *
     * @return 找到的枚举对象，没找到则返回 {@code null}
     * @apiNote 推荐枚举类默认实现该方法
     **/
    @JsonCreator
    public static SampleEnum from(String value) {
        for (SampleEnum e : SampleEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 判断枚举值是否存在
     *
     * @return 是否存在枚举值
     * @apiNote 推荐枚举类默认实现该方法
     **/
    public static boolean exist(String value) {
        return from(value) != null;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@link java.lang.Enum#toString()} 方法默认返回枚举的名字，如：SAMPLE1，实际使用中该方法比较鸡肋，基本上不会用到，但如
     * 果项目使用 Swagger，而枚举类型作为参数时 Swagger 会默认调用枚举对象的 toString() 方法在界面上展示，因此将 toString()
     * 按 value-name 格式输出重写以提高接口文档可读性
     **/
    @Override
    public String toString() {
        return this.value + "-" + this.name;
    }
}