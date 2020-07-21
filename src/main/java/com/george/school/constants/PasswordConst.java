package com.george.school.constants;

/**
 * <p>
 *     密码常量类
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/21 17:08
 * @since JDK 1.8
 */
public class PasswordConst {
    /**
     * 安全密码(UUID生成)，作为盐值用于用户密码的加密
     */
    public static final String SECURITY_KEY = "929123f8f17944e8b0a531045453e1f1";

    /**
     * 程序默认的错误状态码
     */
    public static final int DEFAULT_ERROR_CODE = 500;

    /**
     * 程序默认的成功状态码
     */
    public static final int DEFAULT_SUCCESS_CODE = 200;
}
