package com.george.school.config;

import com.george.school.enums.UserStatusEnum;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.service.IResourcesService;
import com.george.school.service.IRoleService;
import com.george.school.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;

/**
 * <p>
 *     这个类是参照JDBCRealm写的，主要是自定义了如何查询用户信息，如何查询用户的角色和权限，如何校验密码等逻辑
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 14:36
 * @since JDK 1.8
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IResourcesService resourcesService;


    /**
     * 授权
     * 定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        LoginUserDto loginUserDto = (LoginUserDto) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        log.info("当前登录用户 ===>  {}", loginUserDto.getUsername());
        log.info("获取角色信息  ===>  {}", loginUserDto.getRoles());
        log.info("获取权限信息  ===>  {}", loginUserDto.getPerms());
        info.setRoles(loginUserDto.getRoles());
        info.setStringPermissions(loginUserDto.getPerms());
        return info;
    }

    /**
     * 认证
     * 定义如何获取用户信息的业务逻辑，给shiro做登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("请输入用户名");
        }
        LoginUserDto userDB = userService.findUserByName(username);
        if (userDB == null) {
            throw new UnknownAccountException("用户不存在");
        }
        if (userDB.getStatus() != UserStatusEnum.NORMAL.getCode()) {
            throw new LockedAccountException("账号已被锁定，禁止登录!");
        }
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        // 角色集合
        Set<String> roles = roleService.getRolesByUserId(userDB.getId());
        // 权限集合
        Set<String> perms = resourcesService.getResourcesByUserId(userDB.getId());
        userDB.getRoles().addAll(roles);
        userDB.getPerms().addAll(perms);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPassword(), new MySimpleByteSource(userDB.getUsername()), getName());
        //if (userDB.getUsername() != null) {
            // info.setCredentialsSalt(ByteSource.Util.bytes(userDB.getSalt()));
            // info.setCredentialsSalt(new MySimpleByteSource(userDB.getUsername()));
        //}
        return info;
    }
}
