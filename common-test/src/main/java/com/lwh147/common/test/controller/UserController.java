package com.lwh147.common.test.controller;

import com.lwh147.common.core.model.PageData;
import com.lwh147.common.core.util.BeanUtil;
import com.lwh147.common.test.api.UserApi;
import com.lwh147.common.test.entity.User;
import com.lwh147.common.test.pojo.dto.UserAddDTO;
import com.lwh147.common.test.pojo.dto.UserUpdateDTO;
import com.lwh147.common.test.pojo.query.UserQuery;
import com.lwh147.common.test.pojo.vo.UserVO;
import com.lwh147.common.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController implements UserApi {
    @Resource
    private UserService userService;

    @Override
    public Boolean add(@Valid UserAddDTO userAddDTO) {
        return this.userService.add(BeanUtil.convert(userAddDTO, User.class));
    }

    @Override
    public Boolean delete(Long id) {
        return this.userService.delete(id);
    }

    @Override
    public UserVO getById(Long id) {
        return this.userService.getById(id);
    }

    @Override
    public PageData<UserVO> query(@Valid UserQuery userQuery) {
        return this.userService.query(userQuery);
    }

    @Override
    public Boolean update(@Valid UserUpdateDTO userUpdateDTO) {
        return this.userService.update(BeanUtil.convert(userUpdateDTO, User.class));
    }
}
