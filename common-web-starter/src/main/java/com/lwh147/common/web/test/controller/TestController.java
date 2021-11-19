package com.lwh147.common.web.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 测试控制器
 *
 * @author lwh
 * @date 2021/11/15 10:19
 **/
@Slf4j
@RestController
public class TestController {
    @PostMapping("common/client/testPost")
    public String test(@RequestParam("name") String name, @RequestBody Map<String, Object> map) {
        log.debug("name: {}", name);
        log.debug("map: {}", map);
        return "SUCCESS";
    }

    @GetMapping("common/admin/testGet")
    public String test(@RequestParam("name") String name) {
        log.debug("name: {}", name);
        return "SUCCESS";
    }
}
