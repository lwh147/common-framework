package com.lwh147.common.mybatisplus.generator.code;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * 代码生成器配置
 *
 * @author lwh
 * @date 2024/05/07 17:16
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateConfig {
    /**
     * 生成文件的输出目录
     */
    private String outputDir = System.getProperty("user.dir") + File.separator + "generator";
    /**
     * 开发人员
     */
    private String author = "mybatis-plus-generator";

    /**
     * 数据库类型
     */
    private DbType dbType = DbType.MYSQL;
    /**
     * jdbc驱动
     */
    private String jdbcDriver;
    /**
     * 数据库连接地址
     */
    private String jdbcUrl;
    /**
     * 数据库账号
     */
    private String jdbcUserName;
    /**
     * 数据库密码
     */
    private String jdbcPassword;

    /**
     * 代码生成的类的父包名称
     */
    private String parentPackage;
    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 生成的表实体类型
     **/
    private TableTypeEnum tableType = TableTypeEnum.DATA;
    /**
     * 去掉表的前缀
     */
    private String[] tablePrefix;
    /**
     * 代码生成包含的表，可为空，为空默认生成所有
     */
    private String[] includeTables;

}
