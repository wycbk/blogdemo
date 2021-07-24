package com.cheng;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Scanner;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/24 14:16
 **/
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        DataSourceConfig dsc = new DataSourceConfig
                .Builder("jdbc:mysql://localhost:3306/vueblog?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "root", "123")
                .build();
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);

//        String moduleName = scanner("模块名");
        String projectPath = System.getProperty("user.dir");

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig
                .Builder()
                .outputDir(projectPath + "/src/main/java")
                .author("Cheng Jun")
                .openDir(false)
                .build();

        // 包配置
        PackageConfig packageConfig = new PackageConfig
                .Builder()
                .parent("com.cheng")
                .moduleName(null)
                .build();

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig
                .Builder()
                .mapperXml(null)
                .build();

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig
                .Builder()
                .addInclude(scanner("表名，多个英文逗号分割").split(","))
                .entityBuilder().naming(NamingStrategy.underline_to_camel)
                .build();

        mpg.global(globalConfig);
        mpg.packageInfo(packageConfig);
        mpg.template(templateConfig);
        mpg.strategy(strategyConfig);
        mpg.execute(new FreemarkerTemplateEngine());
    }

}