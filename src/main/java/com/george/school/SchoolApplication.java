package com.george.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@SpringBootApplication
@EnableTransactionManagement // 开启事务支持
public class SchoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }
}
