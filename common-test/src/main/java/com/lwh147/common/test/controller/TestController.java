package com.lwh147.common.test.controller;

import com.lwh147.common.core.model.PageQuery;
import com.lwh147.common.core.model.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 测试控制器
 *
 * @author lwh
 * @date 2021/11/15 10:19
 **/
@Slf4j
@RestController
@Api(tags = "测试控制器")
public class TestController {
    @PostMapping("common/testPost")
    @ApiOperation(value = "测试post请求")
    public PageQuery<PageQuery.DefaultSortColumnEnum> test1(@RequestParam("name") String name, @RequestBody @Valid PageQuery<PageQuery.DefaultSortColumnEnum> pageQuery) {
        log.debug("收到post请求并处理");
        return pageQuery;
    }

    @GetMapping("common/testGet")
    @ApiOperation(value = "测试get请求")
    public PageQuery<PageQuery.DefaultSortColumnEnum> test2(@Valid PageQuery<PageQuery.DefaultSortColumnEnum> pageQuery) {
        log.debug("收到get请求并处理");
        return pageQuery;
    }

    @PutMapping("common/testPut")
    @ApiOperation(value = "测试put请求")
    public RespBody<?> test3(@RequestBody @Valid RespBody<?> respBody) {
        log.debug("收到put请求并处理");
        return respBody;
    }

    @DeleteMapping("common/testDelete/{id}")
    @ApiOperation(value = "测试delete请求")
    public String test4(@PathVariable("id") String id) {
        log.debug("收到delete请求并处理");
        return id;
    }
}
