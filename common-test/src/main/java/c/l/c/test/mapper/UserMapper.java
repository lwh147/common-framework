package c.l.c.test.mapper;

import c.l.c.test.entity.User;
import c.l.c.test.pojo.query.UserQuery;
import c.l.c.test.pojo.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 *
 * @author lwh
 * @date 2021/12/13 11:20
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据Id查询用户
     *
     * @param id 用户ID
     * @return 查到的用户对象
     **/
    User getById(@Param("id") Long id);

    /**
     * 根据查询条件分页查询用户
     *
     * @param page      分页信息
     * @param condition 查询条件
     * @return 分页结果
     **/
    Page<UserVO> query(@Param("page") Page<UserVO> page,
                       @Param("condition") UserQuery condition);
}