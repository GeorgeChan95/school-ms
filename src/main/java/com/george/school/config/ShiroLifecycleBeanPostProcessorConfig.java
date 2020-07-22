package com.george.school.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     为解决 ShrioConfig 中 使用注解@Autowired注入的对象为Null的问题
 *     需要把Shiro生命周期函数bean剥离出来
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/22 20:40
 * @since JDK 1.8
 */
@Configuration
public class ShiroLifecycleBeanPostProcessorConfig {
    /**
     * 为了保证实现了Shiro内部lifecycle函数的bean执行 也是shiro的生命周期，注入LifecycleBeanPostProcessor
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
