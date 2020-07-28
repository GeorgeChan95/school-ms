package com.george.school.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     自定义配置类
 *     读取配置文件中config下面的配置，并自动组装成对象
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/28 16:55
 * @since JDK 1.8
 */
@Data
@Component
@ConfigurationProperties(prefix = "config")   //添加此注解自动将配置文件的值赋予下列属性
public class ConfigProperties {
    /**
     * 项目启动后是否使用系统默认浏览器打开登录页，默认开启
     */
    private boolean autoOpenBrowser;
    /**
     * Excel单次导入最大数据量，如 300个数据一次commit
     */
    private int maxBatchInsertNum;
    /**
     * 验证码有效时间，单位秒
     */
    private int codeTime;
    /**
     * 验证码图片格式，png
     */
    private String codeType;
    /**
     * 验证码图片宽度，px
     */
    private int codeWidth;
    /**
     * 验证码图片高度，px
     */
    private int codeHeight;
    /**
     * 验证码位数
     */
    private int codeLength;
    /**
     * 验证码类型：1. 数字+字母；2. 纯数字；3. 纯字母
     */
    private int codeCharType;
}
