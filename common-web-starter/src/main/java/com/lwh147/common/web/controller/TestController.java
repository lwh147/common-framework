package com.lwh147.common.web.controller;

import com.lwh147.common.core.exception.BusinessExceptionEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author lwh
 * @date 2021/11/15 10:19
 **/
@RestController
public class TestController {
    @GetMapping("/test")
    String test() {
        this.test2();
        return "SUCCESS";
    }

    private void test2() {
        throw BusinessExceptionEnum.BUSINESS_ERROR.toException("业务失败");
    }
}
