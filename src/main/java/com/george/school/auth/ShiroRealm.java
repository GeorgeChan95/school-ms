package com.george.school.auth;

import cn.hutool.core.util.ObjectUtil;
import com.george.school.entity.User;
import com.george.school.enums.UserStatusEnum;
import com.george.school.service.IResourcesService;
import com.george.school.service.IRoleService;
import com.george.school.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * <p>
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/28 17:10
 * @since JDK 1.8
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    private IUserService userService;
    private IRoleService roleService;
    private IResourcesService resourcesService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setResourcesService(IResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principals 用户信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        Set<String> roleSet = this.roleService.getRolesByUserId(user.getId());
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        Set<String> permissionSet = this.resourcesService.getResourcesByUserId(user.getId());
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param token 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // 通过用户名到数据库查询用户信息
        User user = this.userService.findByName(username);

        if (ObjectUtil.isNull(user)) {
            throw new UnknownAccountException("用户不存在");
        }
        if (user == null || !StringUtils.equals(password, user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }

        if (UserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
