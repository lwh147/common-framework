package com.lwh147.common.test.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwh147.common.core.model.PageData;
import com.lwh147.common.core.util.BeanUtil;
import com.lwh147.common.test.entity.User;
import com.lwh147.common.test.mapper.UserMapper;
import com.lwh147.common.test.pojo.query.UserQuery;
import com.lwh147.common.test.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户服务
 *
 * @author lwh
 * @date 2021/12/6 17:15
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends ServiceImpl<UserMapper, User> {
    public static final String CACHE_PREFIX = "common:userService:";
    public static final String USER_CACHE_NAME = CACHE_PREFIX + "user";
    public static final String USER_PAGE_CACHE_NAME = CACHE_PREFIX + "userPage";

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Boolean add(User user) {
        return this.baseMapper.insert(user) == 1;
    }

    @CacheInvalidate(name = USER_CACHE_NAME, key = "#id")
    public Boolean delete(Long id) {
        return this.baseMapper.deleteById(id) == 1;
    }

    @Cached(name = USER_CACHE_NAME, key = "#id", cacheType = CacheType.BOTH)
    public UserVO getById(Long id) {
        return BeanUtil.convert(this.userMapper.getById(id), UserVO.class);
    }

    @Cached(name = USER_PAGE_CACHE_NAME, key = "#userQuery", cacheType = CacheType.BOTH)
    public PageData<UserVO> query(UserQuery userQuery) {
        Page<UserVO> pageInfo = userQuery.toPage(UserVO.class);
        this.userMapper.query(pageInfo, userQuery);
        return PageData.fromPage(pageInfo);
    }

    public Boolean update(User user) {
        return this.baseMapper.updateById(user) == 1;
    }
}
