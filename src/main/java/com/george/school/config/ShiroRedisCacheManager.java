package com.george.school.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * <p>
 *     Shiro提供了类似Spring的Cache抽象，即Shiro本身不实现Cache，但是对Cache进行了又抽象，
 *     方便更换不同的底层Cache实现。对应前端的一个页面访问请求会同时出现很多的权限查询操作，
 *     这对于权限信息变化不是很频繁的场景，每次前端页面访问都进行大量的权限数据库查询是非常不经济的。
 *     因此，非常有必要对权限数据使用缓存方案。
 *
 *     由于Spring和Shiro都各自维护了自己的Cache抽象，为防止Realm注入的service里缓存注解和事务注解失效，
 *     所以定义自己的CacheManager处理缓存。
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 16:55
 * @since JDK 1.8
 */
public class ShiroRedisCacheManager implements CacheManager, Destroyable {
    private RedisCacheManager cacheManager;

    public RedisCacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (name == null) {
            return null;
        }
        return new ShiroRedisCache<K, V>(name, getCacheManager());
    }

    @Override
    public void destroy() throws Exception {
        cacheManager = null;
    }
}
