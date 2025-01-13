package com.lwh147.common.util.concurrent;


import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.lwh147.common.util.constant.NumberConstant;

import java.util.concurrent.*;

/**
 * 线程工具类，统一管理线程池，预防OOM
 *
 * <p>
 * ThreadPoolExecutor核心构造方法参数：
 * <pre>
 * {@code corePoolSize} - 核心线程池大小
 * {@code maximumPoolSize} - 最大线程池大小
 * {@code keepAliveTime} - 非核心线程的空闲线程最大存活时间
 * {@code workQueue} - 阻塞任务队列
 * {@code threadFactory} - 新建线程工厂
 * {@code handler} - 当提交任务数超过 {@code maxmumPoolSize + workQueue} 时，任务会交给它来处理
 * </pre>
 * 1. 当线程池中线程数量小于 {@code corePoolSize} ，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程
 * <p>
 * 2. 当线程池中线程数量达到 {@code corePoolSize} 时，新提交任务将被放入 {@code workQueue} 中，等待线程池中任务调度执行
 * <p>
 * *3. 当 {@code workQueue} 已满且 {@code maximumPoolSize > corePoolSize} 时创建新线程
 * <p>
 * 4. 反之当 {@code workQueue} 已满且 {@code maximumPoolSize <= corePoolSize} 时，新提交任务由
 * {@link RejectedExecutionHandler} 处理
 * <p>
 * 5. 当线程池中超过 {@code corePoolSize} 的线程空闲时间达到 {@code keepAliveTime} 时，关闭空闲线程
 * <p>
 * 6. 当设置 {@code allowCoreThreadTimeOut(true)} 时，线程池中 {@code corePoolSize} 线程空闲时间达到 {@code keepAliveTime}
 * 也将关闭
 *
 * <p>
 * 参数设置方式：
 * <p>
 * 《Java并发编程实战》一书中介绍了所需线程数的计算公式：
 * <pre>
 * Ncpu = CPU 核数
 * Ucpu = 目标 CPU 利用率（0 <= Ucpu <= 1）
 * W / C = 线程平均等待时间 / 线程平均计算时间
 *
 * 要程序跑到 CPU 的目标利用率，需要的线程数为：
 *      Nthreads = Ncpu * Ucpu * (1 + W / C)
 * </pre>
 * 可以通过 {@code Runtime.getRuntime().availableProcessors()} 获取逻辑处理器数量
 * <p>
 * 不过这个公式比较理想化，在实际项目中一个服务会运行着很多线程，比如 Tomcat 有自己的线程池、Dubbo 有自己的线程池、GC也有自己
 * 的后台线程，各种框架、中间件也都有自己的工作线程，这些线程也都会占用 CPU 资源，所以通过此公式计算出来的结果是存在一定误差的，
 * 最优解就是通过压测不断的动态调整线程池参数，观察 CPU 利用率、系统负载、内存、吞吐量等各种综合指标数据来找到一个相对比较合理
 * 的值（<a href="https://www.zhihu.com/question/413973145/answer/2674598213">原文</a>）
 *
 * <p>
 * 简易设置：
 * <p>
 * - I/O密集型任务（JavaWeb后端程序一般都是该类型）：
 * <pre>
 *     线程数 = 逻辑处理器数量 * 2 + 1
 * </pre>
 * - 计算密集型任务：
 * <pre>
 *     线程数 = 逻辑处理器数量 + 1
 * </pre>
 *
 * @author lwh
 * @date 2021/12/3 20:23
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/index.html">官方文档</a>
 **/
public final class ThreadUtils {
    /**
     * 分配给当前JVM虚拟机的处理器数量，注意是逻辑处理器数量
     **/
    public static final int PROCESSOR_NUM = Runtime.getRuntime().availableProcessors();
    /**
     * 分配给当前JVM虚拟机的内存大小，单位：字节
     **/
    public static final long TOTAL_MEMORY = Runtime.getRuntime().totalMemory();
    /**
     * 当前JVM虚拟机的剩余内存大小，单位：字节
     **/
    public static final long FREE_MEMORY = Runtime.getRuntime().freeMemory();
    /**
     * 默认线程大小，6MB
     **/
    public static final long DEFAULT_THREAD_SIZE = NumberConstant.M2 * 3;
    /**
     * 线程池计数
     **/
    public static final int EXECUTOR_COUNT = 1;
    /**
     * 默认线程池内线程名称前缀
     **/
    public static final String DEFAULT_EXECUTOR_NAME_PREFIX = "default-exec-worker-";
    /**
     * 默认线程池
     **/
    private static final ExecutorService DEFAULT_EXECUTOR;

    static {
        // 根据可用内存大小及装载因子计算阻塞队列大小
        final int BLOCK_QUEUE_SIZE = FREE_MEMORY > DEFAULT_THREAD_SIZE ?
                (int) (FREE_MEMORY * NumberConstant.DEFAULT_LOAD_FACTOR / DEFAULT_THREAD_SIZE) : 1;
        DEFAULT_EXECUTOR = new ThreadPoolExecutor(
                (int) (PROCESSOR_NUM * NumberConstant.DEFAULT_LOAD_FACTOR / EXECUTOR_COUNT + 1f),
                (int) (2f * PROCESSOR_NUM * NumberConstant.DEFAULT_LOAD_FACTOR + 1f),
                1L, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(BLOCK_QUEUE_SIZE),
                new ThreadFactoryBuilder().setNamePrefix(DEFAULT_EXECUTOR_NAME_PREFIX).build());
        // 预加载线程池
        ((ThreadPoolExecutor) DEFAULT_EXECUTOR).prestartAllCoreThreads();
    }

    private ThreadUtils() {
    }

    /**
     * 获取默认线程池
     **/
    public static ExecutorService getDefaultExecutor() {
        return DEFAULT_EXECUTOR;
    }

    public static void main(String[] args) {
        System.out.println(PROCESSOR_NUM);
        System.out.println(TOTAL_MEMORY);
        System.out.println(FREE_MEMORY);
        System.out.println(FREE_MEMORY > DEFAULT_THREAD_SIZE ?
                (int) (FREE_MEMORY * NumberConstant.DEFAULT_LOAD_FACTOR / DEFAULT_THREAD_SIZE) : 1);
    }
}