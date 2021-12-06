package com.lwh147.common.test.pojo;

import com.lwh147.common.core.model.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户查询条件
 *
 * @author lwh
 * @date 2021/12/6 17:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PageQuery<PageQuery.DefaultSortColumnEnum> {
    private String name;
}
