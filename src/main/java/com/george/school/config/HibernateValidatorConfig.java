package com.george.school.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/11 20:59
 * @since JDK 1.8
 */
/**
 *
 *  2019年11月23日  下午5:15:47
 *   Hibernate Validator有以下两种验证模式：
 *       1、普通模式（默认是这个模式）：普通模式(会校验完所有的属性，然后返回所有的验证失败信息)
 *       2、快速失败返回模式：快速失败返回模式(只要有一个验证失败，则返回)
 *
 *  1、failFast：true  快速失败返回模式    false 普通模式
 *  ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
 *      .configure()
 *       .failFast( true )
 *       .buildValidatorFactory();
 * Validator validator = validatorFactory.getValidator();
 *
 *  2、（hibernate.validator.fail_fast：true  快速失败返回模式    false 普通模式）
 *  ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
 *        .configure()
 *        .addProperty( "hibernate.validator.fail_fast", "true" )
 *        .buildValidatorFactory();
 * Validator validator = validatorFactory.getValidator();
 *
 */
@Configuration
public class HibernateValidatorConfig {
    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }
}
