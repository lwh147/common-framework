package com.lwh147.common.test.controller;

import com.lwh147.common.core.model.PageData;
import com.lwh147.common.test.api.UserApi;
import com.lwh147.common.test.entity.User;
import com.lwh147.common.test.pojo.dto.UserAddDTO;
import com.lwh147.common.test.pojo.dto.UserUpdateDTO;
import com.lwh147.common.test.pojo.query.UserQuery;
import com.lwh147.common.test.pojo.vo.UserVO;
import com.lwh147.common.test.service.UserService;
import com.lwh147.common.util.BeanUtil;
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
    public Boolean add(@RequestBody @Valid UserAddDTO userAddDTO) {
        return this.userService.add(BeanUtil.convert(userAddDTO, User.class));
    }

    @DeleteMapping(value = "/delete/{id}")
    @Override
    public Boolean delete(@PathVariable("id") Long id) {
        return this.userService.delete(id);
    }

    @GetMapping(value = "/{id}")
    @Override
    public UserVO getById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    @PostMapping(value = "/query")
    @Override
    public PageData<UserVO> query(@RequestBody @Valid UserQuery userQuery) {
        return this.userService.query(userQuery);
    }

    @PutMapping(value = "/update")
    @Override
    public Boolean update(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        return this.userService.update(BeanUtil.convert(userUpdateDTO, User.class));
    }
}
