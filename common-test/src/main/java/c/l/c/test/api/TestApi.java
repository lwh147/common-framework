package c.l.c.test.api;

import c.l.c.test.pojo.query.UserQuery;
import com.lwh147.common.core.enums.CommonSortColumnEnum;
import com.lwh147.common.core.enums.SortOrderEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 测试控制器接口
 *
 * @author lwh
 * @date 2021/12/13 16:05
 **/
@Api(tags = "测试控制器")
@RequestMapping("/test")
public interface TestApi {

    @ApiOperation(value = "临时测试")
    void test();

    @GetMapping("/queryParamsWithoutAnnotation")
    @ApiOperation(value = "测试无请求参数注解的参数绑定情况")
    void test1(UserQuery query);

    @GetMapping("/queryParamsWithEnums")
    @ApiOperation(value = "测试枚举请求参数绑定情况")
    void test2(@RequestParam SortOrderEnum order, @RequestParam CommonSortColumnEnum sortColumnEnum);

    @PostMapping("/delayMessage")
    @ApiOperation(value = "测试发送延时消息")
    void test3();

    @PostMapping("/orderMessage")
    @ApiOperation(value = "测试普通订单消息")
    void test4();
}