package com.george.school.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.MessageDigest;

/**
 * <p>
 *     MD5加密工具类
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/21 17:06
 * @since JDK 1.8
 */
@Slf4j
public class Md5Util {
    private static final String ALGORITHM_NAME = "md5";

    private static final int HASH_ITERATIONS = 5;

    public static String encrypt(String username, String password) {
        String source = StringUtils.lowerCase(username);
        password = StringUtils.lowerCase(password);
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(source), HASH_ITERATIONS).toHex();
    }
}
