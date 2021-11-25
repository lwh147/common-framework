package com.lwh147.common.mybatisplus.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

/**
 * 基础实体类，只包含绝对必须存在的字段，id、create_time 和 update_time
 * <p>
 * 在关系型数据库中数据表之间存在一对一、一对多、多对多的关系，在处理多对
 * 多映射关系的时候，通常会引入第三张表将多对多关系分解为两个一对多的关系，
 * 而引入的第三张表中存储的仅仅是两个表数据之间的对应关系，所以这种表可以
 * 称为关系表，由于关系表中存储的不是系统中最基本的数据，所以关系表可以不
 * 具有逻辑删除、版本控制等功能
 * <p>
 * 基础实体类不区分关系表和数据表，所以只有必须字段，同时也可以增加灵活度
 * 如果修改时间和更新时间觉得也没必要那可也直接选择继承 {@link Model} 类
 *
 * @author lwh
 * @date 2021/11/25 17:17
 **/
public class BaseModel {
    private Long id;
    private Date createTime;
    private Date updateTime;
}
