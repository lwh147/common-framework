package com.lwh147.common.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 框架测试启动类
 *
 * @author lwh
 * @date 2021/11/15 10:17
 **/
@SpringBootApplication(scanBasePackages = "com.lwh147.common")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
