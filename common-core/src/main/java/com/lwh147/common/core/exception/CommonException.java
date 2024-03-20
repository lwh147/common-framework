package com.lwh147.common.core.exception;

import com.lwh147.common.util.constant.RegExpConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * 异常处理规范：
 * <p>
 * 首先，作为异常应该记录保存三大内容：在哪里（事发地），发生了什么（事发现场），为什么（原因），
 * 在曾经的单服务系统中，Java内置的传统Exception类记录的上述内容已经满足快速排错的需求，然而在
 * 如今的分布式微服务架构系统中，Java内置的传统Exception类记录的内容略显匮乏，在帮助排查错误的能力
 * 上已经捉襟见肘，所以需要进行扩展以存储更多更详细的错误信息来增强异常类帮助排错的能力
 * 基于分布式微服务架构系统，在设计自定义异常基类时，异常类应至少包含以下信息：
 * source（服务名）,  code（异常编码）, message（异常信息）, cause（原因）
 * 其中，source字段应由全局异常处理自动填充（未来考虑加入traceId字段，全局唯一的异常追踪id）
 * <p>
 * 如上文所描述，错误详情和错误原因在 {@link Throwable} 中已经存在，而 {@link RuntimeException}
 * 继承了它，所以自定义异常基类选择继承自 {@link RuntimeException} 复用 {@code detailMessage}
 * 和 {@code cause} 属性，进一步添加 {@link CommonException#source}、
 * {@link CommonException#code}、{@link CommonException#description} 三个属性分别记录
 * 错误来源、错误码和错误描述
 * <p>
 * 首先，异常分为 业务异常 和 非业务异常
 * 针对非业务异常，业务代码不做处理，发生异常时统一由全局异常处理器去拦截处理
 * 针对业务异常，统一异常不需要过多处理（再封装等），打印相关信息并记录日志即可
 * 其次，出现异常时的日志记录应由全局异常完成，业务代码只管抛出即可，禁止越俎代庖出现重复打印异常信息的情况
 * 全局异常处理器处理异常时应将空指针异常、数据库操作相关异常、远程调用相关异常、参数校验相关异常等（包含但不限于）
 * 经常出现的异常做进一步封装处理，其余异常可不做分类统一认为是其它异常
 * <p>
 * 该自定义异常基类遵循上述异常处理规范进行设计
 *
 * <p>
 * 异常信息包含错误来源、错误码、错误描述、错误详情以及错误原因，其中错误描述只是简单的
 * 概述，属于宏观上的错误信息，错误详情描述了发生错误的具体原因，
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
 *
 * @author lwh
 * @apiNote 只能由实现了 {@link ICommonExceptionEnum} 接口的异常枚举类构造产生此异常对象
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
     * 适用于第一种嵌套异常情况，自身就是错误原因
     * <p>
     * 错误详情与错误描述一致
     *
     * @param ice 异常枚举类行为规范接口对象
     **/
    protected CommonException(ICommonExceptionEnum ice) {
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
     * 适用于第一种嵌套异常情况，自身就是错误原因
     *
     * @param ice           异常枚举类行为规范接口对象
     * @param detailMessage 错误原因的详细描述
     **/
    protected CommonException(ICommonExceptionEnum ice, String detailMessage) {
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
    protected CommonException(ICommonExceptionEnum ice, Throwable cause) {
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
    protected CommonException(ICommonExceptionEnum ice, String detailMessage, Throwable cause) {
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