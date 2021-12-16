package com.lwh147.common.core.model;

import com.lwh147.common.core.enums.DefaultSortColumnEnum;

/**
 * 使用默认排序条件的默认分页查询条件封装类，如果只需要默认的按创建时间排序
 * 而不需要指定其它排序字段，那么可以让分页查询条件封装类继承此类
 *
 * @author lwh
 * @date 2021/12/15 15:26
 **/
public class DefaultPageQuery extends PageQuery<DefaultSortColumnEnum> {
}
