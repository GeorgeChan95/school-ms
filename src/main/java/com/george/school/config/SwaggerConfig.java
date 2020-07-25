package com.george.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * <p>
 * Swagger 配置类
 * 自定义swagger的配置信息
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/25 16:27
 * @since JDK 1.8
 */
@Configuration
@EnableSwagger2 // 开启swagger2
public class SwaggerConfig {

    /**
     * 配置swagger的docket的bean实例
     *
     * @param environment 当前环境变量配置
     * @return
     */
    @Bean
    public Docket docket(Environment environment) {
        // 设置要显示swagger的环境（只在开发和测试环境使用）
        Profiles profiles = Profiles.of("dev", "test");
        // 通过 environment.acceptsProfiles 判断当前是否处在设定的环境中
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                // 是否开启swagger，生产环境一般关闭
                .enable(flag)
                .apiInfo(apiInfo())
                .select()
                // 选择哪些接口作为swagger的doc发布(扫描指定的包)
                .apis(RequestHandlerSelectors.basePackage("com.george.school.controller"))
                // .apis(RequestHandlerSelectors.any()) 扫描所有包
                // 过滤指定的路径
                .paths(PathSelectors.ant("/api/**"))
                // .paths(PathSelectors.any()) 所有路径
                .build()
                // 支持的通信协议
                .protocols(newHashSet("http", "https"))
                // 设置必要的授权信息
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());

    }

    /**
     * 自定义swaggger的信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("School Management System Api Doc")
                .description("后端接口SwaggerAPI文档")
                .contact(new Contact("George", "https://github.com/GeorgeChan95/cloud-learning-code", "george_95@126.com"))
                .termsOfServiceUrl("http://localhost:9999/index/home")
                .version("v1.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder().securityReferences(Collections.singletonList(new SecurityReference("BASE_TOKEN", new AuthorizationScope[]{new AuthorizationScope("global", "")}))).build());
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey("BASE_TOKEN", "token", "pass"));
    }

    /**
     * 自定义set方法
     *
     * @param ts
     * @param <T>
     * @return
     */
    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }

}
