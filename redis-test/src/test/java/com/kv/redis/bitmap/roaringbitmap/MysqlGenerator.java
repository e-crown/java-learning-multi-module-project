package com.kv.redis.bitmap.roaringbitmap;

import java.util.*;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * mybatisPlus代码生成器 Created by hanmeng on 2021/8/15.
 */
public class MysqlGenerator {

    // 排除生成的表 自定义
    private static final String[] excludeTable = new String[] {""};
    // 需要生成的表 自定义
    private static final String[] includeTable = new String[] {"identity_gold"};

    // 包名
    private static final String PACKAGE_NAME = "com.kv.redis.mybatis";
    // 模块名称
    private static final String MODULE_NAME = "dal";

    // 公共的子包名 跟下面的 control、dao、entity、thirdSystem、thirdSystem impl生成路径结合使用。配置时一定要"."开头
    // private static final String COMMON_PATH = ".statistics";
    private static final String COMMON_PATH = "";
    // private static final String COMMON_PATH = ".bs";

    // control层生成 存放路径 如：com.hm.dubbopractice.dubboserver.controller;
    private static final String CONTROLLER_PATH = "mvc" + COMMON_PATH;

    // dao层生成 存放路径 如：com.hm.dubbopractice.dubboserver.dao;
    private static final String MAPPER_PATH = "mybatis" + COMMON_PATH;

    // entity bean对象生成 存放路径 如：com.hm.dubbopractice.dubboserver.entity;
    private static final String ENTITY_PATH = "entity" + COMMON_PATH;

    // service层代码生成存放路径 如：com.hm.dubbopractice.dubboserver.thirdSystem;
    private static final String SERVICE_PATH = "service" + COMMON_PATH;

    // thirdSystem impl层代码生成存放路径 如：com.hm.dubbopractice.dubboserver.thirdSystem.impl;
    private static final String SERVICE_IMPL_PATH = SERVICE_PATH + ".impl";

    // Mapper XML生成存放位置 （经测试没有什么用）
    private static final String XML_PATH = "mybatis";

    /**
     * 通常，每个公司都有自己的表定义，在《阿里巴巴Java开发手册》中， 就强制规定表必备三字段：id, gmt_create, gmt_modified。
     * 所以通常我们都会写个公共的拦截器去实现自动填充比如创建时间和更新时间的，无需开发人员手动设置。
     */
    // 自定义实体，公共字段
    private static final String[] SUPER_ENTITY_COLUMNS = new String[] {"user_id"};
    // 代码输出文件的路径
    private static final String OUT_PATH = "D:\\temp\\code";
    // 代码生成者
    private static final String AUTHOR = "v";

    /**
     * JDBC相关配置
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&connectTimeout=5000&socketTimeout=5000&useSSL=false&serverTimezone=GMT%2B8";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "KHUFV897+#12KMDGFSJW";

    /**
     * MySQL 生成
     */
    public static void main(final String[] args) {
        // 数据源配置
        final DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)// 数据库类型
            .setTypeConvert(new MySqlTypeConvert() {
                // 自定义数据库表字段类型转换【可选】
                @Override
                public IColumnType processTypeConvert(final GlobalConfig globalConfig, final String fieldType) {
                    System.out.println("转换类型：" + fieldType);
                    if (fieldType.toLowerCase().contains("tinyint")) {
                        System.out.println(String.format("%s -> %s", fieldType, DbColumnType.INTEGER));
                        return DbColumnType.INTEGER;
                    }
                    if (fieldType.toUpperCase().contains("datetime")) {
                        System.out.println(String.format("%s -> %s", fieldType, DbColumnType.DATE_SQL));
                        return DbColumnType.DATE_SQL;
                    }
                    return super.processTypeConvert(globalConfig, fieldType);
                }
            }).setDriverName(DRIVER).setUsername(USER_NAME).setPassword(PASSWORD).setUrl(URL);

        // 全局配置
        final GlobalConfig config = new GlobalConfig();
        config.setOutputDir(OUT_PATH)// 输出目录
            .setFileOverride(true)// 是否覆盖文件
            .setActiveRecord(true)// 开启 activeRecord 模式
            .setEnableCache(false)// XML 二级缓存
            .setBaseResultMap(false)// XML ResultMap
            .setBaseColumnList(true)// XML columList
            .setAuthor(AUTHOR).setXmlName("%sMapper")// 自定义mapper文件命名，注意 %s 会自动填充表实体属性！
            .setMapperName("%sDao") // 自定义dao文件命名，注意 %s 会自动填充表实体属性！
            .setEntityName("%sEntity")// 自定义entity文件命名，注意 %s 会自动填充表实体属性！
            .setDateType(DateType.ONLY_DATE)// 设置时间类型
            .setIdType(IdType.ID_WORKER)// 设置主键类型
            .setServiceName("%sService")// user -> UserService, 不设置则会: user -> IUserService
        // .setServiceImplName("%sServiceDiy")
        // .setControllerName("%sAction")
        ;

        // 策略配置
        final StrategyConfig strategyConfig = new StrategyConfig();
        // 自定义需要填充的字段
        /*如每张表都有一个创建时间、修改时间
         *而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改。修改时，修改时间会修改，
         *虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
         *使用公共字段填充功能，就可以实现，自动按场景更新了。
         *如下配置
         */
        final List<TableFill> tableFillList = new ArrayList<>();
        // TableFill idField = new TableFill("id", FieldFill.INSERT);//主键是不能作为公共字段填充
        final TableFill createField = new TableFill("create_time", FieldFill.INSERT);
        final TableFill modifiedField = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        // tableFillList.add(idField);
        tableFillList.add(createField);
        tableFillList.add(modifiedField);

        strategyConfig
            // .setVersionFieldName("")//设置乐观锁字段
            // .setCapitalMode(true)// 全局大写命名
            // .setDbColumnUnderline(true)// 全局下划线命名 3.0.7版本该参数已删除
            // .setTablePrefix(new String[]{"unionpay_"})// 此处可以修改为您的表前缀
            .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
            // .setInclude(new String[] {"citycode_org"}) // 需要生成的表
            .setInclude(includeTable)
            // .setExclude(excludeTable) // 排除生成的表
            // 自定义实体，公共字段
            // .setSuperEntityColumns(SUPER_ENTITY_COLUMNS)
            // 自定义需要填充的字段
            .setTableFillList(tableFillList)
            // .entityTableFieldAnnotationEnable(true)//是否强制带上注解
            // 自定义实体父类
            // .setSuperEntityClass("com.baomidou.demo.base.BsBaseEntity")
            // // 自定义 mybatis 父类
            // .setSuperMapperClass("com.baomidou.demo.base.BsBaseMapper")
            // // 自定义 thirdSystem 父类
            // .setSuperServiceClass("com.baomidou.demo.base.BsBaseService")
            // // 自定义 thirdSystem 实现类父类
            // .setSuperServiceImplClass("com.baomidou.demo.base.BsBaseServiceImpl")
            // 自定义 controller 父类
            // .setSuperControllerClass("com.baomidou.demo.TestController")
            // 【实体】是否生成字段常量（默认 false）
            // public static final String ID = "test_id";
            .setEntityColumnConstant(true)
            // 【实体】是否为构建者模型（默认 false）
            // public User setName(String name) {this.name = name; return this;}
            .setEntityBuilderModel(true)
            // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
            .setEntityLombokModel(true)
        // Boolean类型字段是否移除is前缀处理
        // .setEntityBooleanColumnRemoveIsPrefix(true)
        // .setRestControllerStyle(true)
        // .setControllerMappingHyphenStyle(true)
        ;

        // 代码生成器
        final AutoGenerator mpg = new AutoGenerator().setGlobalConfig(config)// 全局配置
            .setDataSource(dataSourceConfig)// 数据源配置
            .setStrategy(strategyConfig)// 策略配置
            // 包配置
            .setPackageInfo(new PackageConfig().setParent(PACKAGE_NAME)// 父包名。自定义包路径
                .setModuleName(MODULE_NAME)// 父模块名。 自定义包路径
                .setController(CONTROLLER_PATH)// 这里是控制器包名，默认 healthdeclareweb
                .setXml(XML_PATH)// Mapper XML包名
                .setMapper(MAPPER_PATH)// dao 包名
                .setEntity(ENTITY_PATH)// Entity包名
                .setService(SERVICE_PATH)// Service包名
                .setServiceImpl(SERVICE_IMPL_PATH)// Service Impl包名
            ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        final Map<String, Object> map = new HashMap<String, Object>();
                        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                        this.setMap(map);
                    }
                }.setFileOutConfigList(
                    Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mybatis.xml.vm") {
                        // 自定义输出文件目录
                        @Override
                        public String outputFile(final TableInfo tableInfo) {
                            return OUT_PATH + "/xml/" + tableInfo.getEntityName() + "Mapper.xml";
                        }
                    })))
            .setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
            // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
            // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
            // .setController("...");
            // .setEntity("...");
            // .setMapper("...");
            // .setXml("...");
            // .setService("...");
            // .setServiceImpl("...");
            );

        // 执行生成
        mpg.execute();
    }

}
