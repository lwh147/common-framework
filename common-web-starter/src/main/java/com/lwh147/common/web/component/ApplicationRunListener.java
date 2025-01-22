package com.lwh147.common.web.component;

import com.lwh147.common.util.Strings;
import com.lwh147.common.util.constant.HttpConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Spring应用启动过程事件监听
 * <p>
 * 由于该监听器相关生命周期回调方法需要在Spring应用上下文初始化前调用，也就是说使用 {@link Component} 等注解时某些生命周期回调
 * 方法是不会被回调的，需要在 resources 目录下新建 META-INFO/spring.factories 文件指定该监听器实现类：
 *
 * <pre>
 *    org.springframework.boot.SpringApplicationRunListener=com.lwh147.common.web.component.ApplicationRunListener
 * </pre>
 *
 * @author lwh
 * @apiNote 在低版本的SpringBoot中（至少2.X及以下），结合SpringCloud使用时会出现 SpringApplicationRunListener 实现类被实例化
 * 多次且各阶段生命周期回调方法也被调用执行多次的问题
 * @date 2024/03/20 11:29
 * @see <a href="https://blog.csdn.net/NEWCIH/article/details/129893523">SpringApplicationRunListener在SpringBoot项目启动过程中执行了两次</a>
 * @see <a href="https://www.cnblogs.com/kukuxjx/p/17373029.html">【SpringBoot】【二】 SpringApplicationRunListeners 监听器执行过程详解</a>
 **/
@Slf4j
public class ApplicationRunListener implements SpringApplicationRunListener {
    /**
     * 必须提供此构造器（参数列表也需一致），否则启动器不能正常反射实例化该监听器类
     **/
    public ApplicationRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        // 这里利用重复调用时context的id后会多次拼接"-1"的特性来控制该方法只在最后一次初始化时被调用
        ConfigurableEnvironment env = context.getEnvironment();
        String appName = env.getProperty("spring.application.name");
        if ((appName + "-1").equals(context.getId())) {
            String protocol = Strings.isNotBlank(env.getProperty("server.ssl.key-store")) ? HttpConstant.Protocol.HTTPS
                    : HttpConstant.Protocol.HTTP;
            String serverPort = env.getProperty("server.port");
            String contextPath = env.getProperty("server.servlet.context-path");
            if (Strings.isBlank(contextPath)) {
                contextPath = "/";
            }
            String hostAddress = "localhost";
            try {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.warn("The host name could not be determined, using `localhost` as fallback");
            }
            String swaggerBaseUrl = "";
            if (Boolean.TRUE.toString().equals(env.getProperty("swagger.enabled", "false"))) {
                swaggerBaseUrl = protocol + "://" + hostAddress + ":" + serverPort + contextPath +
                        (contextPath.length() == 1 ? "" : "/");
            }
            log.info("\n\n" +
                            "-------------------------------------------------------------------------------------\n" +
                            "Application '{}' is successfully started! Access URLs:\n" +
                            "   Local: {}://localhost:{}{}\n" +
                            "External: {}://{}:{}{}\n" +
                            " Swagger: {}\n" +
                            " Knife4j: {}\n" +
                            "-------------------------------------------------------------------------------------\n",
                    appName, protocol, serverPort, contextPath, protocol, hostAddress,
                    serverPort, contextPath, swaggerBaseUrl + "swagger-ui.html", swaggerBaseUrl + "doc.html");
        }
    }
}