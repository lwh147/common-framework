package com.lwh147.common.core.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * CompletableFuture工具类，必须指定线程池使用，预防OOM
 *
 * @author lwh
 * @date 2021/12/3 20:23
 **/
public final class CompletableFutureUtils {

    private CompletableFutureUtils() {}

    /**
     * 使用默认线程池运行带有返回值的异步计算任务
     **/
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, ThreadUtils.getDefaultExecutor());
    }

    /**
     * 使用指定线程池运行带有返回值的异步计算任务
     **/
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    /**
     * 使用默认线程池运行无返回值的异步计算任务
     **/
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, ThreadUtils.getDefaultExecutor());
    }

    /**
     * 使用指定线程池运行无返回值的异步计算任务
     **/
    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }
}