package com.lwh147.common.core.exception;

import com.lwh147.common.util.constant.RegExpConstant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 本框架基于分布式微服务架构系统设计扩展后的增强异常类
 * <p>
 * 作为异常应该记录保存三大内容：在哪里（事发地），发生了什么（事发现场），为什么（原因），
 * 在曾经的单服务系统中，Java内置的传统Exception类记录的上述内容已经满足快速排错的需求，然而在
 * 如今的分布式微服务架构系统中，Java内置的传统Exception类记录的内容略显匮乏，在帮助排查错误的能力
 * 上已经捉襟见肘，所以需要进行扩展以存储更多更详细的信息来增强异常类帮助排错的能力，这个类就是
 * 本框架基于分布式微服务架构系统设计扩展后的增强异常类，该类包含以下信息：
 * <p>
 * traceId（全局唯一的异常追踪id）、source（异常源头微服务名）、at（异常发生的代码位置）、code（异常枚举编码）、
 * name（异常枚举名称）、message（异常信息）、cause（异常原因）
 * <p>
 * traceId是为了方便在日志中寻找异常日志，根据id可直接搜索定位到异常日志；
 * source是为了直接体现出发生异常的源头服务，开发可直接去source指定的服务日志中使用traceId查找日志，
 * 就不需要从异常链路头部开始一个个微服务找过去；
 * at字段实际上是提取了异常堆栈中的栈顶调用信息，大部分情况下异常堆栈栈顶所指代码位置就是异常现场
 * （当然也可能是JDK或其它依赖库中的代码），这样方便快速定位异常代码位置；
 * code和name就是一般项目中定义的错误枚举信息，方便非开发人员处理业务相关的问题，对于业务异常的处理比较有帮助；
 * 以上的traceId、source、at由增强异常类或本框架的全局异常处理器自动填充，剩余字段需要开发者开发过程中自行填充。
 * message异常信息和cause原因在 {@link Throwable} 中已经存在，而 {@link RuntimeException}
 * 继承了它，所以该类选择继承 {@link RuntimeException} 复用 {@code detailMessage}
 * 和 {@code cause} 属性（对应上文所述的 message 和 cause ），进一步添加 {@link CommonException#traceId}、
 * {@link CommonException#source}、{@link CommonException#code}、{@link CommonException#name}、
 * {@link CommonException#at}
 * <p>
 * 通常项目中的异常类型会分为 业务异常 和 非业务异常：
 * 针对非业务异常，开发写代码时可以完全不做处理，发生异常时统一由全局异常处理器去拦截处理；
 * 针对业务异常，统一异常不需要过多处理（再封装等），打印相关信息并记录日志即可。
 * 出现异常时的日志记录应由全局异常完成，业务代码只管抛出即可，禁止越俎代庖出现重复打印异常信息的情况。
 * <p>
 * 推荐使用该框架的开发者将空指针异常、数据库操作相关异常、远程调用相关异常、参数校验相关异常等（包含但不限于）
 * 经常出现的异常转换为增强异常，实现本框架提供的转换接口即可，参见 {@link EnhancedExceptionConverter}
 * <p>
 * Java中的异常是嵌套异常，发生异常的情况分为两种：
 * <p>
 * 第一种：当前环境就是异常发生的第一现场，例如通过手动编写判断逻辑（多为if语句）
 * 认定发生了异常，这类异常本身就是原因，所以 {@link Throwable} 中的原因
 * {@code cause} 属性在不指定原因的情况下默认指向自己，即 {@code cause=this}
 * <p>
 * 第二种：try...catch语句中捕获到了其它异常导致当前代码发生异常，即当前环境不是异常
 * 发生的第一现场，在以catch语句捕获到的异常对象 {@code e} 为头的异常链中（ {@code cause}
 * 属性充当了链表指针），末尾节点保存了发生异常的第一现场，也就是根本原因，所以必须
 * 使用catch到的异常对象 {@code e} 构造完整的异常嵌套链，即让 {@link Throwable} 中
 * 的 {@code cause} 属性指向 {@code e}，否则就会发生吞异常的情况，即看不到问题发生
 * 的第一现场，更别说排查问题原因了
 * <p>
 * 针对不同情况，本增强异常类提供了多种异常构造方式
 * <p>
 * 为方便统一管理系统异常，本增强异常类推荐由实现了 {@link EnhancedRuntimeExceptionEnum} 接口
 * 的异常枚举类构造产生，即继承本类之后构造器访问权限建议设置为 {@code protect}，这样可以避免开发
 * 过程中出现一堆开发自行定义的异常而非系统规定的异常
 *
 * @author lwh
 * @date 2024/11/23 15:00
 **/
@Getter
@EqualsAndHashCode(callSuper = true)
public class EnhancedRuntimeException extends RuntimeException {
    /**
     * 全局唯一的异常追踪id
     **/
    @Setter
    private String traceId;
    /**
     * 异常源头微服务名
     **/
    @Setter
    private String source;
    /**
     * 异常代码行
     **/
    private final String at;
    /**
     * 异常枚举编码
     **/
    private final String code;
    /**
     * 异常枚举名称
     **/
    private final String name;

    /**
     * 只包含异常枚举信息
     *
     * @param e 异常枚举
     **/
    protected EnhancedRuntimeException(EnhancedRuntimeExceptionEnum e) {
        super();
        this.code = e.getCode();
        this.name = e.getName();
        this.at = this.getStackTrace()[1].toString();
    }

    /**
     * 包含异常枚举信息以及异常信息
     *
     * @param e       异常枚举
     * @param message 异常信息
     **/
    protected EnhancedRuntimeException(EnhancedRuntimeExceptionEnum e, String message) {
        super(message);
        this.code = e.getCode();
        this.name = e.getName();
        this.at = this.getStackTrace()[1].toString();
    }

    /**
     * 包含异常枚举信息以及异常原因
     *
     * @param e     异常枚举
     * @param cause 异常原因
     **/
    protected EnhancedRuntimeException(EnhancedRuntimeExceptionEnum e, Throwable cause) {
        // 去除获取到的message中可能存在的格式化字符
        super(RegExpConstant.ENTER_PATTERN.matcher(cause.getMessage()).replaceAll(""), cause);
        this.code = e.getCode();
        this.name = e.getName();
        this.at = cause.getStackTrace()[0].toString();
    }

    /**
     * 包含所有信息
     *
     * @param e       异常枚举
     * @param message 异常信息
     * @param cause   异常原因
     **/
    protected EnhancedRuntimeException(EnhancedRuntimeExceptionEnum e, String message, Throwable cause) {
        super(message, cause);
        this.code = e.getCode();
        this.name = e.getName();
        this.at = cause.getStackTrace()[0].toString();
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
                "traceId=" + traceId +
                ", source=" + source +
                ", at=" + at +
                ", code=" + code +
                ", name=" + name +
                ", message=" + super.getMessage() +
                "}";
    }
}