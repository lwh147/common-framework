package c.l.c.test.api;

import c.l.c.test.pojo.dto.UserAddDTO;
import c.l.c.test.pojo.dto.UserUpdateDTO;
import c.l.c.test.pojo.query.UserQuery;
import c.l.c.test.pojo.vo.UserVO;
import com.lwh147.common.core.enums.SortOrderEnum;
import com.lwh147.common.core.schema.response.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/user/add")
    @ApiOperation(value = "新增用户")
    Boolean add(@RequestBody UserAddDTO userAddDTO);

    /**
     * 根据删除用户
     *
     * @param id 要删除的用户ID
     * @return 是否删除成功
     **/
    @DeleteMapping("/user/delete/{id}")
    @ApiOperation(value = "删除用户")
    Boolean delete(@PathVariable("id") Long id);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 查询到的用户对象，没有找到返回null
     **/
    @GetMapping("/user/{id}")
    @ApiOperation(value = "根据ID查询用户")
    UserVO getById(@PathVariable("id") Long id);

    /**
     * 根据查询条件查询用户
     *
     * @param userQuery 用户查询条件封装类
     * @return 查询结果
     **/
    @PostMapping("/user/query")
    @ApiOperation(value = "查询用户")
    PageData<UserVO> query(@RequestBody UserQuery userQuery);

    /**
     * 更新用户
     *
     * @param userUpdateDTO 用来更新的用户实体类对象
     * @return 更新是否成功
     **/
    @PutMapping("/user/update")
    @ApiOperation(value = "更新用户")
    Boolean update(@RequestBody UserUpdateDTO userUpdateDTO);

    /**
     * test
     **/
    @GetMapping("/test")
    @ApiOperation(value = "test")
    void test(UserQuery order);

    /**
     * test
     **/
    @GetMapping("/test1")
    @ApiOperation(value = "test1")
    void test1(@RequestParam SortOrderEnum order);
}