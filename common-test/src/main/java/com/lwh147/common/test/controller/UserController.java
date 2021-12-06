package com.lwh147.common.test.controller;

import com.lwh147.common.core.model.PageData;
import com.lwh147.common.test.pojo.User;
import com.lwh147.common.test.pojo.UserQuery;
import com.lwh147.common.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户控制器
 *
 * @author lwh
 * @date 2021/11/15 10:19
 **/
@Slf4j
@RestController
@Api(tags = "用户控制器")
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/add")
    @ApiOperation(value = "新增用户")
    public String add(@RequestBody @Valid User user) {
        return this.userService.add(user);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除用户")
    public String delete(@PathVariable("id") Long id) {
        return this.userService.delete(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询用户")
    public User getById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    @PostMapping("/query")
    @ApiOperation(value = "查询用户")
    public PageData<User> query(@RequestBody @Valid UserQuery userQuery) {
        return this.userService.query(userQuery);
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新用户")
    public String test3(@RequestBody @Valid User user) {
        return this.userService.update(user);
    }
}
