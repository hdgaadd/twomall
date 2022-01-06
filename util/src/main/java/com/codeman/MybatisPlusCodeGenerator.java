package com.codeman;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * Created by hdgaadd on 2021/12/06/18:25
 */
public class MybatisPlusCodeGenerator {

    // 读取控制台内容
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 设置Servic、Controller层为空
        TemplateConfig config = new TemplateConfig();
        config.setService(null);
        config.setServiceImpl(null);
        config.setController(null);

        // 从Libraries复制源文件，修改实体类或Mapper的代码
        config.setEntity("templates/entity2.java");
        config.setMapper("templates/mapper2.java");
        mpg.setTemplate(config);


        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        System.out.println("please input your module name");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        // 先得到当前工程目录
        String projectPath = System.getProperty("user.dir");
        // 是maven项目的结构，就是工程目录 + /src/main/java
        gc.setOutputDir(projectPath + "/" + name + "/src/main/java");


        // 开启映射结果集
        gc.setBaseResultMap(true);
        // 开启查询结果列
        // gc.setBaseColumnList(true);


        // 修改文件后缀名
        gc.setEntityName("%sDO");
        // gc.setServiceName("%sDO");


        // 设置生成文件的作者信息
        gc.setAuthor("hdgaadd");

        //当代码生成完成之后是否打开代码所在的文件夹
        gc.setOpen(false);

        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        //gc.setServiceName("%sService");

        // 将上述的全局配置注入
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dataSourceConfiguration = new DataSourceConfig();

        dataSourceConfiguration.setUrl("jdbc:mysql://localhost:3306/twomall#63;useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");

        // dataSourceConfiguration.setSchemaName("public");
        dataSourceConfiguration.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfiguration.setUsername("root");
        dataSourceConfiguration.setPassword("root");

        mpg.setDataSource(dataSourceConfiguration);

        // 包配置
        PackageConfig pc = new PackageConfig();

        // 设置父级包名
        pc.setParent("com.codeman");//controller entity service service.impl

        // pc.setModuleName(scanner("'com.'后面的模块名"));
        //pc.setModuleName("sys");

        // 实体类名称
        pc.setEntity("domain");

        // mapper包名称
        pc.setMapper("mapper");

        // mapper对应的映射器xml
        pc.setXml("mapper.xml");

        // 业务包层名称
        pc.setService("service");

        // 业务接口的实现类包
        pc.setServiceImpl("service.impl");

        // 控制器包名称
        pc.setController("controller");

        // 装填包信息对象
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        //设置字段和表名的是否把下划线完成驼峰命名规则
        strategy.setNaming(NamingStrategy.underline_to_camel);

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        //设置生成的实体类继承的父类
        //strategy.setSuperEntityClass("com.sxt.BaseEntity");

        //是否启动lombok
        strategy.setEntityLombokModel(true);

        //是否生成resetController
        strategy.setRestControllerStyle(true);

        // 公共父类
        //strategy.setSuperControllerClass("com.sxt.BaseController");

        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("person_id","person_name");

        //要设置生成哪些表 如果不设置就是生成所有的表
        System.out.println("if you create all tables, 1 is true, 0 is false");
        boolean flag = true;
        if (sc.nextInt() == 0) strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));

        strategy.setControllerMappingHyphenStyle(true);

        //strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setTablePrefix("sys_");

        mpg.setStrategy(strategy);

        mpg.execute();
    }
}