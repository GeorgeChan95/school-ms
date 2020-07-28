package com.george.school.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * shiro自定义属性配置类
 * 读取配置文件中shiro下面的配置，并自动组装成对象
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/28 16:49
 * @since JDK 1.8
 */
@Component
@Data
@ConfigurationProperties(prefix = "shiro")   //添加此注解自动将配置文件的值赋予下列属性
public class ShiroProperties {
    /**
     * session 超时时间，单位为秒
     */
    private int sessionTimeout;
    /**
     * rememberMe cookie有效时长，单位为秒
     */
    private int cookieTimeout;
    /**
     * 免认证的路径配置，如静态资源等
     */
    private String anonUrl;
    /**
     * 登录 url
     */
    private String loginUrl;
    /**
     * 首页 url
     */
    private String successUrl;
    /**
     * 登出 url
     */
    private String logoutUrl;
    /**
     * 未授权跳转 url
     */
    private String unauthorizedUrl;
    /**
     * rememberMe cookie 加密的密钥
     */
    private String encryptKey;
}