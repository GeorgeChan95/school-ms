package com.george.school.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.george.school.realm.CustomRealm;
import com.george.school.util.CollectionSerializer;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import javax.servlet.Filter;
import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * Shiro 配置类
 * shiro所有的配置入口
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 14:22
 * @since JDK 1.8
 */
@Configuration
@Order(-1)
public class ShiroConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 然后呢在只需要吧这个Realm注册到Spring容器中就可以啦
     *
     * @return
     */
    @Bean
    public CustomRealm customRealm(RedisCacheManager redisCacheManager) {
        CustomRealm realm = new CustomRealm();
        realm.setCachingEnabled(true);
        //设置认证密码算法及迭代复杂度
        //realm.setCredentialsMatcher(credentialsMatcher());
        //认证
        realm.setCacheManager(shiroRedisCacheManager(redisCacheManager));
        realm.setAuthenticationCachingEnabled(true);
        //授权
        realm.setAuthorizationCachingEnabled(true);
        //这里主要是缓存key的名字
        realm.setAuthenticationCacheName("redisauthen");
        realm.setAuthorizationCacheName("redisauthor");
        return realm;
    }

    /**
     * 为了保证实现了Shiro内部lifecycle函数的bean执行 也是shiro的生命周期，注入LifecycleBeanPostProcessor
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 紧接着配置安全管理器，
     * SecurityManager是Shiro框架的核心，典型的Facade模式，
     * Shiro通过SecurityManager来管理内部组件实例，并通过它来提供安全管理的各种服务。
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(RedisTemplate redisTemplate, RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm(redisCacheManager));
        securityManager.setCacheManager(shiroRedisCacheManager(redisCacheManager));
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager(redisTemplate));
        return securityManager;
    }

    /**
     * 除此之外Shiro是一堆一堆的过滤链，所以要对shiro 的过滤进行设置，
     * 这里小心踩坑！我在application.yml中设置的context-path: /api/v1
     * 但经过实际测试，过滤器的过滤路径，是context-path下的路径，无需加上"/api/v1"前缀
     * @return
     */
//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        chainDefinition.addPathDefinition("favicon.ico", "anon");
//        chainDefinition.addPathDefinition("/login", "anon");
//        chainDefinition.addPathDefinition("/**", "user");
//        return chainDefinition;
//    }

    /**
     * 除此之外Shiro是一堆一堆的过滤链，所以要对shiro 的过滤进行设置，
     * 不需要在此处配置权限页面,因为上面的ShiroFilterFactoryBean已经配置过,
     * 但是此处必须存在,因为shiro-spring-boot-web-starter或查找此Bean,没有会报错
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        return new DefaultShiroFilterChainDefinition();
    }


    /**
     * shiro过滤连
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 添加自己的过滤器并且取名
        Map<String, Filter> filterMap = new HashMap<>(16);
//        filterMap.put("my", new MyFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        filterChainDefinitionMap.put("login", "anon");
        filterChainDefinitionMap.put("/user/test", "anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
     * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，
     * 导致返回404。加入这项配置能解决这个bug
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * "记住我"
     * 生成cookie模板，生成cookie的name和有效时间
     * 所以shiro规定记住我功能最多得user级别的，不能到authc级别
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * 配置rememberMeManager
     * rememberMeManager()方法是生成rememberMe管理器，
     * 而且要将这个rememberMe管理器设置到securityManager中。
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * redisTemplate
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * Spring缓存管理器配置
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        CollectionSerializer<Serializable> collectionSerializer = CollectionSerializer.getInstance();
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(collectionSerializer));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    /**
     * shiro缓存管理器的配置
     *
     * @param redisCacheManager
     * @return
     */
    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager(RedisCacheManager redisCacheManager) {
        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager();
        cacheManager.setCacheManager(redisCacheManager);
        //name是key的前缀，可以设置任何值，无影响，可以设置带项目特色的值
        return cacheManager;
    }

    /**
     * 配置sessionmanager，由redis存储数据
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(RedisTemplate redisTemplate) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        CollectionSerializer<Serializable> collectionSerializer = CollectionSerializer.getInstance();
        redisTemplate.setDefaultSerializer(collectionSerializer);
        //redisTemplate默认采用的其实是valueSerializer，就算是采用其他ops也一样，这是一个坑。
        redisTemplate.setValueSerializer(collectionSerializer);
        ShiroRedisSessionDao redisSessionDao = new ShiroRedisSessionDao(redisTemplate);
        //这个name的作用也不大，只是有特色的cookie的名称。
        sessionManager.setSessionDAO(redisSessionDao);
        sessionManager.setDeleteInvalidSessions(true);
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("starrkCookie");
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }
}
