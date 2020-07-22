package com.george.school.config;

import com.george.school.entity.User;
import com.george.school.service.IResourcesService;
import com.george.school.service.IRoleService;
import com.george.school.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

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
public class CustomRealm extends AuthorizingRealm {
    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
//    {
//        //设置用于匹配密码的CredentialsMatcher
//        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
//        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
//        hashMatcher.setStoredCredentialsHexEncoded(false);
//        hashMatcher.setHashIterations(1024);
//        this.setCredentialsMatcher(hashMatcher);
//    }
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
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        User user = (User) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        System.out.println("获取角色信息：" + user.getRoles());
//        System.out.println("获取权限信息：" + user.getPerms());
//        info.setRoles(user.getRoles());
//        info.setStringPermissions(user.getPerms());
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
        // Null username is invalid
        if (username == null) {
            throw new AccountException("请输入用户名");
        }
//        User userDB = userService.findUserByName(username);
        User userDB = new User();
        if (userDB == null) {
            throw new UnknownAccountException("用户不存在");
        }
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
//        Set<String> roles = roleService.getRolesByUserId(userDB.getUid());
//        Set<String> perms = resourcesService.getResourcesByUserId(userDB.getUid());
//        userDB.getRoles().addAll(roles);
//        userDB.getPerms().addAll(perms);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPassword(), getName());
//        if (userDB.getSalt() != null) {
////            info.setCredentialsSalt(ByteSource.Util.bytes(userDB.getSalt()));
//            info.setCredentialsSalt(new MySimpleByteSource(userDB.getSalt()));
//        }
        return info;
    }
}
