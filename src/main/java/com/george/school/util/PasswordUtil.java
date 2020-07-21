package com.george.school.util;

import com.george.school.constants.PasswordConst;

/**
 * <p>
 *     密码工具类，密码的加密与解密
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/21 17:04
 * @since JDK 1.8
 */
public class PasswordUtil {
    /**
     * AES 加密
     * @param password
     *         未加密的密码
     * @param salt
     *         盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String encrypt(String password, String salt) throws Exception {
        return AesUtil.encrypt(Md5Util.MD5(salt + PasswordConst.SECURITY_KEY), password);
    }

    /**
     * AES 解密
     * @param encryptPassword
     *         加密后的密码
     * @param salt
     *         盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptPassword, String salt) throws Exception {
        return AesUtil.decrypt(Md5Util.MD5(salt + PasswordConst.SECURITY_KEY), encryptPassword);
    }
}
