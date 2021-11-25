package com.lwh147.common.core.exception;

import com.lwh147.common.core.constant.RegExpConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * 自定义非业务异常
 * <p>
 * 该异常包含错误来源、错误码、错误描述、错误详情以及错误原因，其中错误描述只是简单的
 * 一个错误的限定描述，属于宏观上的二级错误，错误详情包含了发生错误的原因的详细描述，
 * 方便排错，错误原因比较复杂，因为Java中的异常是嵌套异常，发生异常的情况分为两种：
 * <p>
 * 第一种：当前环境就是异常发生的第一现场，例如通过手动编写判断逻辑（多为if语句）
 * 认定发生了异常，这类异常本身就是错误原因，所以 {@link Throwable} 中的错误原因
 * {@code cause} 属性在不指定错误原因的情况下默认指向自己，即 {@code cause=this}
 * <p>
 * 第二种：try...catch语句中捕获到了其它异常导致当前代码发生异常，即当前环境不是异常
 * 发生的第一现场，在以catch语句捕获到的异常对象 {@code e} 为头的异常链中（ {@code cause}
 * 属性充当了链表指针），末尾节点保存了发生错误的第一现场，也就是根本的错误原因，所以必须
 * 使用catch到的异常对象 {@code e} 构造完整的嵌套异常链，即让 {@link Throwable} 中
 * 的 {@code cause} 属性指向 {@code e}，否则就会发生异常吞噬的情况，即丢失了发生
 * 错误的第一现场，找不到根本的错误原因
 * <p>
 * 针对不同的情况，提供了多种不同的异常构造方式，详细说明请参考方法注释
 * <p>
 * 如上文所描述，错误详情和错误原因在 {@link Throwable} 中已经存在，而
 * {@link RuntimeException} 继承了它，所以本类继承自 {@link RuntimeException}
 * 复用 {@code detailMessage} 和 {@code cause} 属性，进一步添加 {@code source}、
 * {@code code}、{@code description} 三个属性分别记录错误来源、错误码和错误描述
 * <p>
 *
 * @author lwh
 * @apiNote 只能由实现了 {@code ICommonExceptionEnum} 接口的异常枚举类构造产生此异常对象
 * @date 2021/10/22 10:47
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonException extends RuntimeException implements ICommonException {
    /**
     * 错误来源
     **/
    private String source;
    /**
     * 错误码
     **/
    private String code;
    /**
     * 错误描述
     **/
    private String description;

    /**
     * 基本构造方式，不推荐
     * <p>
     * 只包含简单的错误码和错误描述
     * <p>
     * 属于第一种嵌套异常情况，自身就是错误原因
     * <p>
     * 错误详情与错误描述一致
     *
     * @param ice 异常枚举类行为规范接口对象
     **/
    public CommonException(ICommonExceptionEnum ice) {
        // 没有错误详情所以使用错误描述作为错误详情
        // 默认cause=this
        super(ice.getDescription());
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    /**
     * 较为完整的构造方式，推荐
     * <p>
     * 包含错误码、错误描述以及错误详情
     * <p>
     * 属于第一种嵌套异常情况，自身就是错误原因
     *
     * @param ice           异常枚举类行为规范接口对象
     * @param detailMessage 错误原因的详细描述
     **/
    public CommonException(ICommonExceptionEnum ice, String detailMessage) {
        // 默认cause=this
        super(detailMessage);
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    /**
     * 完整构造方式1
     * <p>
     * 包含错误码、错误描述以及错误原因
     * <p>
     * 适用于第二种嵌套异常情况，带有错误原因
     * <p>
     * 错误详情使用错误原因对象的原始信息
     *
     * @param ice   异常枚举类行为规范接口对象
     * @param cause 错误原因，一个异常对象
     **/
    public CommonException(ICommonExceptionEnum ice, Throwable cause) {
        // 去除获取到的message中可能存在的格式化字符
        super(RegExpConstant.ENTER_PATTERN.matcher(cause.getMessage()).replaceAll(""), cause);
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    /**
     * 完整构造方式2，推荐
     * <p>
     * 包含错误码、错误描述、错误原因以及错误详情
     * <p>
     * 适用于第二种嵌套异常情况，带有错误原因
     *
     * @param ice           异常枚举类行为规范接口对象
     * @param detailMessage 错误原因的详细描述
     * @param cause         错误原因，一个异常对象
     **/
    public CommonException(ICommonExceptionEnum ice, String detailMessage, Throwable cause) {
        super(detailMessage, cause);
        this.code = ice.getCode();
        this.description = ice.getDescription();
    }

    @Override
    public String toString() {
        return MessageFormat.format("CommonException(source={0}, code={1}, description={2}, detailMessage={3})",
                this.source, this.code, this.description, super.getMessage());
    }
}
