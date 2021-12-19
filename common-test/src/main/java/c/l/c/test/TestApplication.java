package c.l.c.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 框架测试启动类
 *
 * @author lwh
 * @date 2021/11/15 10:17
 **/
@SpringBootApplication(scanBasePackages = {
        "com.lwh147.common",
        "c.l.c.test"
})
// @EnableMethodCache(basePackages = "c.l.c.test.service")
// @EnableCreateCacheAnnotation
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
