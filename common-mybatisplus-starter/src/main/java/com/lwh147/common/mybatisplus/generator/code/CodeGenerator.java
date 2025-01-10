package com.lwh147.common.mybatisplus.generator.code;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.lwh147.common.mybatisplus.model.BaseDataModel;
import com.lwh147.common.mybatisplus.model.BaseDataVersionModel;
import com.lwh147.common.mybatisplus.model.BaseModel;

/**
 * 代码生成器
 *
 * @author lwh
 * @date 2024/05/07 17:16
 **/
public final class CodeGenerator {

    public static void main(String[] args) {
        GenerateConfig config = new GenerateConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?useSSL=false&characterEncoding=UTF-8&serverTimeZone=GMT%2B8");
        config.setJdbcUserName("root");
        config.setJdbcPassword("123456");
        config.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        config.setAuthor("test");
        config.setParentPackage("com.lwh147");
        config.setModuleName("test");
        config.setTablePrefix(new String[]{"t_"});
        config.setIncludeTables(new String[]{"t_integral_user"});
        config.setTableType(TableTypeEnum.DATA);
        CodeGenerator.execute(config);
    }

    public static void execute(GenerateConfig generateConfig) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = getGlobalConfig(generateConfig);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(generateConfig.getDbType());
        dsc.setDriverName(generateConfig.getJdbcDriver());
        dsc.setUrl(generateConfig.getJdbcUrl());
        dsc.setUsername(generateConfig.getJdbcUserName());
        dsc.setPassword(generateConfig.getJdbcPassword());
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = getStrategyConfig(generateConfig);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = getPackageConfig(generateConfig);
        mpg.setPackageInfo(pc);

        // 模板配置
        TemplateConfig tc = new TemplateConfig();
        tc.setController("controller.java.vm");
        tc.setEntity("entity.java.vm");
        tc.setMapper("mapper.java.vm");
        tc.setXml("mapper.xml.vm");
        tc.setService("service.java.vm");
        tc.setServiceImpl("serviceImpl.java.vm");

        // 执行生成
        mpg.execute();
    }

    private static PackageConfig getPackageConfig(GenerateConfig generateConfig) {
        PackageConfig pc = new PackageConfig();
        // 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(generateConfig.getParentPackage());
        // 父包模块名
        pc.setModuleName(generateConfig.getModuleName());
        // controller包名
        pc.setController("controller");
        // service包名
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        // 实体类包名
        pc.setEntity("entity");
        // mapper.java包名
        pc.setMapper("mapper");
        // mapper.xml包名
        pc.setXml("mapper.xml");
        return pc;
    }

    private static StrategyConfig getStrategyConfig(GenerateConfig generateConfig) {
        StrategyConfig strategy = new StrategyConfig();
        // 是否大写命名
        // strategy.setCapitalMode(false);
        // 是否跳过视图
        // strategy.setSkipView(false);
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 表前缀
        if (generateConfig.getTablePrefix() != null && generateConfig.getTablePrefix().length > 0) {
            strategy.setTablePrefix(generateConfig.getTablePrefix());
        }

        if (TableTypeEnum.BASE.equals(generateConfig.getTableType())) {
            // 自定义继承的 Entity 类全称，带包名
            strategy.setSuperEntityClass("com.lwh147.common.mybatisplus.model.BaseModel");
            // 自定义基础的 Entity 类，公共字段
            strategy.setSuperEntityColumns(BaseModel.ID, BaseModel.CREATE_TIME, BaseModel.UPDATE_TIME);
        } else if (TableTypeEnum.DATA.equals(generateConfig.getTableType())) {
            strategy.setSuperEntityClass("com.lwh147.common.mybatisplus.model.BaseDataModel");
            strategy.setSuperEntityColumns(BaseDataModel.ID, BaseDataModel.CREATE_TIME, BaseDataModel.UPDATE_TIME, BaseDataModel.DELETED);
        } else {
            strategy.setSuperEntityClass("com.lwh147.common.mybatisplus.model.BaseDataVersionModel");
            strategy.setSuperEntityColumns(BaseDataVersionModel.ID, BaseDataVersionModel.CREATE_TIME, BaseDataVersionModel.UPDATE_TIME, BaseDataVersionModel.DELETED, BaseDataVersionModel.VERSION);
        }

        // 默认激活进行 sql 模糊表名匹配
        // strategy.setEnableSqlFilter(true);
        // 需要包含的表名，为空默认生成所有
        strategy.setInclude(generateConfig.getIncludeTables());
        // 【实体】是否生成字段常量
        strategy.setEntityColumnConstant(true);
        // 【实体】是否为链式模型
        // strategy.setChainModel(false);
        // 【实体】是否为 lombok 模型
        strategy.setEntityLombokModel(true);
        // Boolean 类型字段是否移除 is 前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 是否生成实体时，生成字段注解
        // strategy.setEntityTableFieldAnnotationEnable(false);

        return strategy;
    }

    private static GlobalConfig getGlobalConfig(GenerateConfig generateConfig) {
        GlobalConfig gc = new GlobalConfig();
        // 生成文件的输出目录
        gc.setOutputDir(generateConfig.getOutputDir());
        // 是否覆盖已有文件
        gc.setFileOverride(true);
        // 是否打开输出目录
        gc.setOpen(false);
        // 是否在 xml 中添加二级缓存配置
        // gc.setEnableCache(false);
        // 开发人员
        gc.setAuthor(generateConfig.getAuthor());
        // 开启 Kotlin 模式
        // gc.setKotlin(false);
        // 开启 swagger2 模式
        gc.setSwagger2(true);
        // 开启 ActiveRecord 模式，实体类型增加泛型信息
        gc.setActiveRecord(true);
        // 开启 BaseResultMap
        gc.setBaseResultMap(true);
        // 开启 baseColumnList
        gc.setBaseColumnList(true);
        // 时间类型对应策略
        gc.setDateType(DateType.ONLY_DATE);
        // 指定生成的主键的 ID 类型
        gc.setIdType(IdType.ASSIGN_ID);
        // service名称
        gc.setServiceName("%sService");
        return gc;
    }
}
