package com.george.school.auth;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *     Shiro Session 监听
 *     spring 使用 shiro 后，由于shiro重新封装了原有的session，所以不能再使用原来的session监听方法了
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/28 17:33
 * @since JDK 1.8
 */
public class ShiroSessionListener implements SessionListener {
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
}
