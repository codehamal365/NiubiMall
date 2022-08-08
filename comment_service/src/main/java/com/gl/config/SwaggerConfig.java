package com.gl.config;

/**
 * @author Kan
 * @Time 2021-11-05-9:54
 */

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/swagger-ui.html
 * http://localhost:8080/v2/api-docs
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //.host("host") // 设置host, 如果前端文档有代理，在SwaggerFilter中进行过滤并删除这个属性，否则接口无法调用
                .select()
                /*
                下面这行代码是告诉 Swagger 要扫描有 @Api 注解的类，
                可以将 Api.class 替换成 ApiOperation.class 扫描带有 @ApiOperation 注解的方法。
                当然还可以使用 basePackage() 方法配置 Swagger 需要扫描的包路径。
                */
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot")
                .description("RESTFul接口文档说明")
                .version("1.0")
                .build();
    }
}