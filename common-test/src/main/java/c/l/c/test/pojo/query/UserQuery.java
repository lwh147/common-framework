package c.l.c.test.pojo.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwh147.common.core.enums.CommonSortColumnEnum;
import com.lwh147.common.core.schema.request.SortPageQuery;
import com.lwh147.common.util.JacksonUtils;
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
public class UserQuery extends SortPageQuery<CommonSortColumnEnum> {
    @ApiModelProperty(value = "name", example = "李四")
    private String name;

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        final String json = "{\n" +
                "\t\"column\": \"createTime\",\n" +
                "\t\"current\": 1,\n" +
                "\t\"name\": \"李四\",\n" +
                "\t\"order\": \"DESC\",\n" +
                "\t\"size\": 10,\n" +
                "\t\"test\": \"1\"\n" +
                "}";
        System.out.println(JacksonUtils.toJsonStr(JacksonUtils.parseObject(json, UserQuery.class)));
    }
}