package com.lwh147.common.test.controller;

import com.lwh147.common.core.model.PageData;
import com.lwh147.common.test.api.UserApi;
import com.lwh147.common.test.entity.User;
import com.lwh147.common.test.pojo.UserQuery;
import com.lwh147.common.test.service.UserService;
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
@RequestMapping(value = "/user")
public class UserController implements UserApi {
    @Resource
    private UserService userService;

    @PostMapping(value = "/add")
    @Override
    public Boolean add(@RequestBody @Valid User user) {
        return this.userService.add(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Override
    public Boolean delete(@PathVariable("id") Long id) {
        return this.userService.delete(id);
    }

    @GetMapping(value = "/{id}")
    @Override
    public User getById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    @PostMapping(value = "/query")
    @Override
    public PageData<User> query(@RequestBody @Valid UserQuery userQuery) {
        return this.userService.query(userQuery);
    }

    @PutMapping(value = "/update")
    @Override
    public Boolean update(@RequestBody @Valid User user) {
        return this.userService.update(user);
    }
}
