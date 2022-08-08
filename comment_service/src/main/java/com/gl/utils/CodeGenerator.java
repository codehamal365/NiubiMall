package com.gl.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/edu_comment?serverTimezone=UTC&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.author("Long") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\LENOVO\\Desktop\\code"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.gl") // 设置父包名
//                            .moduleName("goods") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\LENOVO\\Desktop\\code\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("course_comment","course_comment_favorite_record") // 设置需要生成的表名
                            .addTablePrefix("c_","t_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
