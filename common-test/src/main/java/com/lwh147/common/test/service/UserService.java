package com.lwh147.common.test.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.lwh147.common.core.model.PageData;
import com.lwh147.common.test.pojo.User;
import com.lwh147.common.test.pojo.UserQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户服务
 *
 * @author lwh
 * @date 2021/12/6 17:15
 **/
@Service
public class UserService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Cached(name = "UserService.User.", key = "#user.id")
    public String add(User user) {
        return "SUCCESS";
    }

    @CacheInvalidate(name = "UserService.user", key = "#user.id")
    public String delete(Long id) {
        return "SUCCESS";
    }

    @Cached(name = "UserService.User.", key = "#user.id")
    public User getById(Long id) {
        return new User(1L, "张三", "男", 18, "我是新增的用户", new Date());
    }

    @Cached(name = "UserService.User.", key = "#user.id")
    public PageData<User> query(UserQuery userQuery) {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "张三", "男", 18, "我是新增的用户", new Date()));
        PageData<User> userPageData = new PageData<>();
        userPageData.setRecords(userList);
        return userPageData;
    }

    @CacheUpdate(name = "UserService.user", key = "#user.id", value = "#user")
    public String update(User user) {
        return "SUCCESS";
    }
}
