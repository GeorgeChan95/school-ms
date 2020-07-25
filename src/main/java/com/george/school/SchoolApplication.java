package com.george.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * <p>
 *     学生成绩管理系统
 *     启动主类
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/15 21:56
 * @since JDK 1.8
 */
@EnableOpenApi // 开启swagger文档支持
@SpringBootApplication
@EnableTransactionManagement // 开启事务支持
public class SchoolApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
        LOGGER.info("程序启动成功......");
    }
}
