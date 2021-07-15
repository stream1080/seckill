package com.example.demo;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java"); //文件输出路径
        gc.setAuthor("stream");  //作者名
        gc.setOpen(false);
        gc.setFileOverride(true);  //是否覆盖
        gc.setServiceName("%sService"); //去掉IService前面的I
        gc.setIdType(IdType.ID_WORKER);   //Id数据类型
        gc.setDateType(DateType.ONLY_DATE);   //时间类型
        gc.setSwagger2(false);  //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/seckill?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.example.demo");  //设置包名
        pc.setEntity("entity");         //实体类包名
        pc.setMapper("mapper");         //mapper类包名
        pc.setService("service");       //service类包名
        pc.setController("controller"); //控制器包名
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("t_user","t_goods","t_order","t_seckill_goods","t_seckill_order");  //生成的表，可接多个参数
        strategy.setTablePrefix("t_"); //去掉表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);//驼峰命名转换
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//驼峰命名转换
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);  //是否试用Lombok
        strategy.setRestControllerStyle(true); //是否使用Rest风格的Controller
        strategy.setLogicDeleteFieldName("deleted");//设置逻辑删除
        //        TableField creatTime = new TableField("create_time", FieldFill.INSERT);//自动填充创建时间
        //        TableField updateTime = new TableField("update_time", FieldFill.UPDATE);//自动填充更新时间
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        mpg.execute();
    }
}
