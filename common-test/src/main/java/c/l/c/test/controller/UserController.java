package c.l.c.test.controller;

import c.l.c.test.api.UserApi;
import c.l.c.test.entity.User;
import c.l.c.test.pojo.dto.UserAddDTO;
import c.l.c.test.pojo.dto.UserUpdateDTO;
import c.l.c.test.pojo.query.UserQuery;
import c.l.c.test.pojo.vo.UserVO;
import c.l.c.test.service.UserService;
import com.lwh147.common.core.schema.response.PageData;
import com.lwh147.common.util.BeanUtil;
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

    @Override
    public void test() {
        throw new NullPointerException("test");
    }
}