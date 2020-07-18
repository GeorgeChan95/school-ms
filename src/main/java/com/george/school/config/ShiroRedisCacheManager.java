package com.george.school.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * <p></p>
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
