package com.farben.springboot.xiaozhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //http://localhost:8080/swagger-ui.html
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 指定扫描的包路径，这里表示扫描com.example.demo.controller包下的所有控制器
                .apis(RequestHandlerSelectors.basePackage("com.farben.springboot.xiaozhang.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot集成Swagger 2构建RESTful API文档")
                .description("这是一个使用Swagger 2生成的API文档，方便前后端开发人员进行接口对接和调试。")
                .version("1.0.0")
                .build();
    }
}