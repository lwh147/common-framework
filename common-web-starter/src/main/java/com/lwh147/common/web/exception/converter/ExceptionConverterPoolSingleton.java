package com.lwh147.common.web.exception.converter;

import com.lwh147.common.core.constant.RegExpConstant;
import com.lwh147.common.core.exception.CommonExceptionEnum;
import com.lwh147.common.core.exception.ICommonException;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 单例模式的异常转换器池
 *
 * @author lwh
 * @date 2021/11/10 16:15
 **/
@Slf4j
@SuppressWarnings("rawtypes")
public class ExceptionConverterPoolSingleton {
    /**
     * 异常转换器池，根据转换器所接收的异常类型进行存储
     * <p>
     * 每个异常转换器实际上也只会实例化产生一个转换器对象
     **/
    private final Map<Type, IExceptionConverter> pool;
    /**
     * 该类的单例对象
     **/
    private static ExceptionConverterPoolSingleton exceptionConverter;

    /**
     * 禁止外部实例化
     **/
    private ExceptionConverterPoolSingleton(@Nullable String customPackage) {
        this.pool = new HashMap<>(16);
        // 扫描基础包路径
        final String defaultPackageName = "com.lwh147.common.web.exception.converter";
        Reflections reflections;
        if (Objects.nonNull(customPackage)) {
            // 添加用户自定义扫描包路径
            log.debug("自定义的异常转换器扫描包路径[{}]", customPackage);
            reflections = new Reflections(defaultPackageName, customPackage);
        } else {
            reflections = new Reflections(defaultPackageName);
        }
        Set<Class<? extends IExceptionConverter>> subTypes = reflections.getSubTypesOf(IExceptionConverter.class);
        for (Class<? extends IExceptionConverter> cls : subTypes) {
            // 实例化转换器对象并根据其转换的异常类型放入转换器池中
            this.pool.put(getType(cls), newInstance(cls));
        }
        // 有循环，输出前先判断
        if (log.isDebugEnabled()) {
            log.debug("扫描到的异常转换器：");
            for (Map.Entry<Type, IExceptionConverter> e : pool.entrySet()) {
                log.debug("Converter: {}, Type: {}", e.getClass().toString(), e.getKey().toString());
            }
        }
    }

    /**
     * 通过此方法获取该类的唯一实例对象
     *
     * @param customPackage 用户自定义的异常转换器扫描包路径
     * @return {@link ExceptionConverterPoolSingleton} 异常转换器池
     **/
    public static ExceptionConverterPoolSingleton newInstance(@Nullable String customPackage) {
        // 不为null直接返回
        if (Objects.nonNull(exceptionConverter)) {
            return exceptionConverter;
        }
        /*
         * 为null，说明该类还没有一个实例对象，进行实例化操作
         * 对类对象加锁，防止并发和并行操作导致实例化多次
         */
        synchronized (ExceptionConverterPoolSingleton.class) {
            // 双重校验
            if (Objects.nonNull(exceptionConverter)) {
                return exceptionConverter;
            }
            // 调用构造方法实例化对象
            log.debug("开始初始化异常转换器池...");
            exceptionConverter = new ExceptionConverterPoolSingleton(customPackage);
            log.debug("异常转换器池初始化完成");
            return exceptionConverter;
        }
    }

    /**
     * 根据传入异常类型从转换器池中获取对应的异常转换器对异常进行转换
     *
     * @param e 待转换的异常对象
     * @return {@link ICommonException} 转换后的异常对象
     **/
    @SuppressWarnings("unchecked")
    public ICommonException convert(Exception e) {
        // 从转换器池中获取对应类型的转换器
        IExceptionConverter converter = this.pool.get(e.getClass());
        if (Objects.nonNull(converter)) {
            // 不为空则调用其转换方法进行转换
            return converter.convert(e);
        }
        // 没有找到
        // 去除获取到的message中可能存在的格式化字符
        String unformattedMessage = RegExpConstant.ENTER_PATTERN.matcher(e.getMessage()).replaceAll("");
        return CommonExceptionEnum.SYSTEM_UNHANDLED_EXCEPTION_ERROR.toException(e.getClass().toString()
                + ": " + unformattedMessage, e);
    }

    /**
     * 获取异常转换器支持转换的异常类型
     *
     * @param cls 异常转换器类对象
     * @return {@link Type} 该转换器支持转换的异常类型
     **/
    private Type getType(Class<? extends IExceptionConverter> cls) {
        final String methodName = "convert";
        // 获取转换方法对象，必须是public的
        Method[] methods = cls.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                // 获取该转换器对象支持转换的异常类型
                return m.getParameterTypes()[0];
            }
        }
        // 没找到
        throw CommonExceptionEnum.COMMON_ERROR.toException("没有找到异常转换器对象"
                + "[" + cls.toGenericString() + "]的[" + methodName + "]方法");
    }

    /**
     * 实例化转换器
     *
     * @param cls 转换器类
     * @return {@link IExceptionConverter} 转换器实例对象
     **/
    private IExceptionConverter newInstance(Class<? extends IExceptionConverter> cls) {
        try {
            // 获取无参构造方法
            Constructor<? extends IExceptionConverter> constructor = cls.getDeclaredConstructor();
            // 设置访问权限
            constructor.setAccessible(true);
            // 实例化对象返回
            return constructor.newInstance();
        } catch (InstantiationException e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("实例化异常转换器对象"
                    + "[" + cls.toGenericString() + "]失败[" + e.getMessage() + "]", e);
        } catch (InvocationTargetException e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("反射执行异常转换器对象"
                    + "[" + cls.toGenericString() + "]的无参构造方法时发生异常[" + e.getMessage() + "]", e);
        } catch (NoSuchMethodException e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("没有找到异常转换器对象"
                    + "[" + cls.toGenericString() + "]的无参构造方法[" + e.getMessage() + "]", e);
        } catch (IllegalAccessException e) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("无法访问异常转换器对象"
                    + "[" + cls.toGenericString() + "]的无参构造方法[" + e.getMessage() + "]", e);
        }
    }
}
