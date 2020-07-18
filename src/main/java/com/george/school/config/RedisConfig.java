package com.george.school.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import redis.clients.jedis.JedisPoolConfig;
import java.time.Duration;

/**
 * <p>
 * Redis配置
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 18:17
 * @since JDK 1.8
 */
@Configuration
@Order(-2)
public class RedisConfig {
    @Configuration
    public static class JedisConf {
        // 单机节点Ip地址
        @Value("${spring.redis.host:127.0.0.1}")
        private String host;
        // 端口号
        @Value("${spring.redis.port:6379}")
        private Integer port;
        // redis密码
        @Value("${spring.redis.password:}")
        private String password;
        // 数据库索引 默认0
        @Value("${spring.redis.database:0}")
        private Integer database;
        // 连接池最大连接数（使用负值表示没有限制）
        @Value("${spring.redis.jedis.pool.max-active:8}")
        private Integer maxActive;
        // 连接池中的最大空闲连接
        @Value("${spring.redis.jedis.pool.max-idle:8}")
        private Integer maxIdle;
        // 连接池最大阻塞等待时间（使用负值表示没有限制）
        @Value("${spring.redis.jedis.pool.max-wait:20000}")
        private Long maxWait;
        // 连接池中的最小空闲连接
        @Value("${spring.redis.jedis.pool.min-idle:0}")
        private Integer minIdle;

        @Bean
        public JedisPoolConfig jedisPool() {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMinIdle(minIdle);
            return jedisPoolConfig;
        }

        @Bean
        public RedisStandaloneConfiguration jedisConfig() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPort(port);
            config.setDatabase(database);
            config.setPassword(RedisPassword.of(password));
            return config;
        }
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPool,
                                                         RedisStandaloneConfiguration jedisConfig) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisConfig);
        return connectionFactory;
    }


    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //key序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(redisSerializer);
        //value hashmap序列化
        RedisSerializer redisHashSerializer = new RedisSerializer() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return JSON.toJSONBytes(o);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                String obj = new String(bytes);
                return obj;
            }
        };
        redisTemplate.setHashValueSerializer(redisHashSerializer);
        redisTemplate.setHashKeySerializer(redisHashSerializer);
        redisTemplate.afterPropertiesSet();
        // 设置不支持事务，可以自动释放连接
        redisTemplate.setEnableTransactionSupport(false);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 配置序列化（解决乱码的问题）,过期时间30秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
}
