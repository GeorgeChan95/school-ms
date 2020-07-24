package com.george.school.service.impl;

import com.george.school.model.vo.ResourceVO;
import com.george.school.service.IResourcesService;
import com.george.school.service.IUserService;
import com.george.school.service.ShiroService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/22 17:22
 * @since JDK 1.8
 */
@Slf4j
@Service
public class ShiroServiceImpl implements ShiroService {
    private final IUserService userService;
    private final IResourcesService resourcesService;

    @Autowired
    public ShiroServiceImpl(IUserService userService, IResourcesService resourcesService) {
        this.userService = userService;
        this.resourcesService = resourcesService;
    }

    /**
     * 常用的过滤器
     * anon: 无须认证登录，可以访问
     * authc: 必须认证才可以访问
     * user: 如果使用rememberMe功能可以直接访问
     * perms: 该资源必须得到资源权限才可以访问
     * role： 该资源必须得到角色权限才可以访问
     */
    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        Map<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        // 配置退出过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/index/login", "anon");
        filterChainDefinitionMap.put("/index/error/**", "anon");
        filterChainDefinitionMap.put("*.html", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/vue/**", "anon");
        //放开swagger-ui地址
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v1/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/captcha.jpg", "anon");
        filterChainDefinitionMap.put("/csrf", "anon");
        filterChainDefinitionMap.put("/treetable-lay/**", "anon");
        // 验证码生成和校验接口
        filterChainDefinitionMap.put("/api/captcha/**", "anon");

        // 加载数据库中配置的菜单权限列表
        List<ResourceVO> resources = resourcesService.findUrlAndPermision();
        resources.stream().filter(r -> StringUtils.isNotEmpty(r.getPermission())
                && StringUtils.isNotEmpty(r.getUrl()))
                .forEach(n -> {
                    String permission = "perms[" + n.getPermission() + "]";
                    filterChainDefinitionMap.put(n.getUrl(), permission);
                });
        // 如果使用rememberMe功能可以直接访问
        filterChainDefinitionMap.put("/index/home", "user");
        // 所有资源都必须认证才能操作(这个必须放在最后面)
        filterChainDefinitionMap.put("/**", "user");
        return filterChainDefinitionMap;
    }
}
