package com.lwh147.common.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwh147.common.test.entity.User;
import com.lwh147.common.test.pojo.query.UserQuery;
import com.lwh147.common.test.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Param;

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
    User getById(@Param("id") Long id);

    Page<UserVO> query(@Param("page") Page<UserVO> page,
                       @Param("condition") UserQuery condition);
}
