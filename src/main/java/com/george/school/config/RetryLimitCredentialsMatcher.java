package com.george.school.config;

import com.george.school.constants.UserSessionConst;
import com.george.school.entity.User;
import com.george.school.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *     用户登录计数器
 *     判断用户是否锁定，
 *     密码错误输入次数
 *     锁定用户登录状态
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/21 16:59
 * @since JDK 1.8
 */
public class RetryLimitCredentialsMatcher extends CredentialsMatcher {
    /**
     * 用户登录次数计数，redisKey 前缀
     */
    private static final String SHIRO_LOGIN_COUNT = "shiro_login_count_";
    /**
     * 用户登录是否被锁定，redisKey 前缀
     */
    private static final String SHIRO_IS_LOCK = "shiro_is_lock";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IUserService userService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 从认证信息中获取用户id
        Long userId = (Long) info.getPrincipals().getPrimaryPrincipal();
        User user = userService.getById(userId);
        String username = user.getUsername();

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        // 登录次数rediskey
        String loginCountKey = SHIRO_LOGIN_COUNT + username;
        // 锁定状态 redisKey
        String isLockKey = SHIRO_IS_LOCK + username;
        // 登录一次计数器加1
        opsForValue.increment(loginCountKey, 1);

        if (redisTemplate.hasKey(isLockKey)) {
            throw new ExcessiveAttemptsException("账号[" + username + "]已被禁止登录！");
        }
        // 3次错误登录，账号锁定1分钟
        // 已尝试登录次数
        String count = String.valueOf(opsForValue.get(loginCountKey));
        int retryCount = 3 - Integer.valueOf(count);
        if (retryCount <= 0) {
            // 账号锁定1分钟
            opsForValue.set(isLockKey, "LOKE", 1, TimeUnit.SECONDS);
            // 计数器1分钟后清除
            redisTemplate.expire(loginCountKey, 1, TimeUnit.SECONDS);
            throw new ExcessiveAttemptsException("由于账户输入密码错误次数过多，账号["+ username+"]已被禁止登陆");
        }

        boolean match = super.doCredentialsMatch(token, info);
        if (!match) {
            String msg = retryCount <= 0 ? "账号1分钟内禁止登陆" : "您还剩 " + retryCount + "登录机会";
            throw new AccountException("账号或密码不正确！" + msg);
        }

        // 输入正确，删除登录次数key
        redisTemplate.delete(loginCountKey);
        /**
         * 更新用户最后登录时间等数据
         * 使用try catch 即使更新失败，不影响登录
         */
        try {
//            userService.updateUserLastLoginInfo(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 把use放入session为了freemarker可以直接从session中取值
        SecurityUtils.getSubject().getSession().setAttribute(UserSessionConst.USER_SESSION_KEY, user);
        return true;
    }
}
