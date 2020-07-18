package com.george.school.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *     Redis存储Session
 *
 *     关于共享session的问题大家都应该知道了，传统的部署项目，
 *     两个相同的项目部署到不同的服务器上，Nginx负载均衡后会导致用户在A上登陆了，
 *     经过负载均衡后，在B上要重新登录，因为A上有相关session信息，而B没有。
 *     这种情况也称为“有状态”服务。而“无状态”服务则是：在一个公共的地方存储session，
 *     每次访问都会统一到这个地方来拿。思路呢就是实现Shiro的Session接口，然后呢自己控制，
 *     这里我们实现AbstractSessionDAO。
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 17:26
 * @since JDK 1.8
 */
@Slf4j
public class ShiroRedisSessionDao extends AbstractSessionDAO {
    private RedisTemplate redisTemplate;

    public ShiroRedisSessionDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        log.info("创建seesion,id=[{}]", session.getId().toString());
        try {
            redisTemplate.opsForValue().set(session.getId().toString(), session, 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("获取seesion,id=[{}]", sessionId.toString());
        Session readSession = null;
        try {
            readSession = (Session) redisTemplate.opsForValue().get(sessionId.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return readSession;
    }

    /**
     * 更新session
     * @param session
     * @throws UnknownSessionException
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        log.info("更新seesion,id=[{}]", session.getId().toString());
        try {
            redisTemplate.opsForValue().set(session.getId().toString(), session, 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Session session) {
        log.info("删除seesion,id=[{}]", session.getId().toString());
        try {
            String key = session.getId().toString();
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        log.info("获取存活的session");
        return Collections.emptySet();
    }
}
