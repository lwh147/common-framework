package com.lwh147.common.test.api;

import com.lwh147.common.core.model.PageData;
import com.lwh147.common.test.pojo.dto.UserAddDTO;
import com.lwh147.common.test.pojo.dto.UserUpdateDTO;
import com.lwh147.common.test.pojo.query.UserQuery;
import com.lwh147.common.test.pojo.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户控制器接口
 *
 * @author lwh
 * @date 2021/12/13 16:05
 **/
@Api(tags = "用户控制器")
public interface UserApi {
    /**
     * 新增用户
     *
     * @param userAddDTO 新增的用户实体类对象
     * @return 新增是否成功
     **/
    @ApiOperation(value = "新增用户")
    Boolean add(UserAddDTO userAddDTO);

    /**
     * 根据删除用户
     *
     * @param id 要删除的用户ID
     * @return 是否删除成功
     **/
    @ApiOperation(value = "删除用户")
    Boolean delete(Long id);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 查询到的用户对象，没有找到返回null
     **/
    @ApiOperation(value = "根据ID查询用户")
    UserVO getById(Long id);

    /**
     * 根据查询条件查询用户
     *
     * @param userQuery 用户查询条件封装类
     * @return 查询结果
     **/
    @ApiOperation(value = "查询用户")
    PageData<UserVO> query(UserQuery userQuery);

    /**
     * 更新用户
     *
     * @param userUpdateDTO 用来更新的用户实体类对象
     * @return 更新是否成功
     **/
    @ApiOperation(value = "更新用户")
    Boolean update(UserUpdateDTO userUpdateDTO);
}
