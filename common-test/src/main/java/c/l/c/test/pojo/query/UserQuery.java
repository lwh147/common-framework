package c.l.c.test.pojo.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lwh147.common.core.enums.CommonSortColumnEnum;
import com.lwh147.common.core.enums.SortOrderEnum;
import com.lwh147.common.core.enums.serializer.EnumDeserializerModifier;
import com.lwh147.common.core.enums.serializer.EnumSerializerModifier;
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
        SimpleModule simpleModule = new SimpleModule();
        // simpleModule.setSerializers(new EnumSerializer());
        // simpleModule.setDeserializers(new EnumDeserializer());
        simpleModule.setSerializerModifier(new EnumSerializerModifier());
        simpleModule.setDeserializerModifier(new EnumDeserializerModifier());
        objectMapper.registerModule(simpleModule);

        final UserQuery userQuery = new UserQuery();
        userQuery.setPage(1L);
        userQuery.setSize(10L);
        userQuery.setOrder(SortOrderEnum.ASC);
        userQuery.setColumn(CommonSortColumnEnum.CREATE_TIME);
        userQuery.setName("test");

        final String json = "{\n" +
                "\t\"column\": \"createTime\",\n" +
                "\t\"page\": 1,\n" +
                "\t\"name\": \"李四\",\n" +
                "\t\"order\": \"desc\",\n" +
                "\t\"size\": 10\n" +
                "}";

        System.out.println("序列化：" + JacksonUtils.toJsonStr(userQuery, objectMapper));
        UserQuery userQuery1 = JacksonUtils.parseObject(json, objectMapper, UserQuery.class);
        System.out.println("反序列化：" + userQuery1);

    }
}