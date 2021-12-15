package com.lwh147.common.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwh147.common.test.entity.User;

/**
 * 用户Mapper
 *
 * @author lwh
 * @date 2021/12/13 11:20
 **/
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据Id查询用户
     *
     * @param id 用户ID
     * @return 查到的用户对象
     **/
    User selectById(Long id);
}
