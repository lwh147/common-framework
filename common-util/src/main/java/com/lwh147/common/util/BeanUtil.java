package com.lwh147.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bean转换工具类，基于 {@link BeanUtils} 封装
 *
 * @author lwh
 * @date 2021/12/3 20:23
 **/
public final class BeanUtil extends BeanUtils {

    private BeanUtil() {}

    /**
     * 复制属性
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <S>    源对象类型
     * @param <T>    目标对象类型
     */
    public static <S, T> void copy(S source, T target) {
        copy(source, target, null);
    }

    /**
     * 复制属性
     *
     * @param source   源对象
     * @param target   目标对象
     * @param callBack 回调方法
     * @param <S>      源对象类型
     * @param <T>      目标对象类型
     */
    public static <S, T> void copy(S source, T target, CallBack<S, T> callBack) {
        if (null == source || null == target) {
            return;
        }
        copyProperties(source, target);
        if (callBack != null) {
            callBack.callBack(source, target);
        }
    }

    /**
     * 转换对象
     *
     * @param source      源对象
     * @param targetClass 目标对象类
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 目标对象，可能为null
     */
    public static <S, T> T convert(S source, Class<T> targetClass) {
        return convert(source, targetClass, null);
    }

    /**
     * 转换对象
     *
     * @param source      源对象
     * @param targetClass 目标对象类
     * @param callBack    回调方法
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 目标对象，可能为null
     */
    public static <S, T> T convert(S source, Class<T> targetClass, CallBack<S, T> callBack) {
        if (null == source || null == targetClass) {
            return null;
        }
        T target;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Reflective operation exception", e);
        }
        copyProperties(source, target);
        if (callBack != null) {
            callBack.callBack(source, target);
        }
        return target;
    }

    /**
     * 转换列表对象
     *
     * @param sources     源对象list
     * @param targetClass 目标对象类
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 目标对象list，可能为null
     */
    public static <S, T> List<T> convertList(List<S> sources, Class<T> targetClass) {
        return convertList(sources, targetClass, null);
    }

    /**
     * 转换列表对象
     *
     * @param sources     源对象list
     * @param targetClass 目标对象类
     * @param callBack    回调方法
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 目标对象list，可能为null
     */
    public static <S, T> List<T> convertList(List<S> sources, Class<T> targetClass, CallBack<S, T> callBack) {
        if (null == sources || null == targetClass) {
            return null;
        }
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T target;
            try {
                target = targetClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Reflective operation exception", e);
            }
            copyProperties(source, target);
            if (callBack != null) {
                callBack.callBack(source, target);
            }
            list.add(target);
        }
        return list;
    }

    /**
     * Map 转换为 Bean
     *
     * @param map         原map
     * @param targetClass 目标Bean类型
     * @return 转换得到的目标Bean对象
     **/
    public static <T> T mapToBean(Map<String, ?> map, Class<T> targetClass) {
        // TODO
        return null;
    }

    /**
     * 回调接口
     *
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     */
    @FunctionalInterface
    public interface CallBack<S, T> {
        /**
         * 自定义拷贝回调方法
         *
         * @param s 源对象
         * @param t 目标对象
         **/
        void callBack(S s, T t);
    }
}