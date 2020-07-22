package com.george.school.config;

import com.george.school.service.IUserService;
import com.george.school.service.ShiroService;
import com.george.school.util.CollectionSerializer;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.io.Serializable;
import java.time.Duration;
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
@DependsOn("redisConfig")
@AutoConfigureAfter(value = ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class);
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

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
        realm.setCredentialsMatcher(credentialsMatcher());
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
     * 紧接着配置安全管理器，
     * SecurityManager是Shiro框架的核心，典型的Facade模式，
     * Shiro通过SecurityManager来管理内部组件实例，并通过它来提供安全管理的各种服务。
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm(redisCacheManager));
        securityManager.setCacheManager(shiroRedisCacheManager(redisCacheManager));
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager(redisTemplate));
        return securityManager;
    }

    /**
     * 凭证匹配器
     * 自定义密码的校验方式，
     * 判断用户是否锁定
     * 密码错误输入次数
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public RetryLimitCredentialsMatcher credentialsMatcher() {
        return new RetryLimitCredentialsMatcher();
    }

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
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
        // 数据库中的权限资源拦截数据加载
        Map<String, String> filterChainDefinitionMap = shiroService.loadFilterChainDefinitions();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * DefaultAdvisorAutoProxyCreator是用来扫描上下文，寻找所有的Advistor(通知器），
     * 将这些Advisor应用到所有符合切入点的Bean中。所以必须在lifecycleBeanPostProcessor创建之后创建，
     * 所以用了@DependsOn
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
     * （这里暂时不用了，redisTemplate 在 RedisConfig配置类中做了初始化）
     * @param connectionFactory
     * @return
     */
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//        template.setValueSerializer(serializer);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }

    /**
     * 采用RedisCacheManager作为缓存管理器
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
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
