package c.l.c.test.pojo.query;

import com.lwh147.common.core.request.SortPageQuery;
import com.lwh147.common.model.enums.BaseSortColumnEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询条件
 *
 * @author lwh
 * @date 2021/12/6 17:36
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户查询条件")
public class UserQuery extends SortPageQuery<BaseSortColumnEnum> {
    @ApiModelProperty(value = "name", example = "李四")
    private String name;
}