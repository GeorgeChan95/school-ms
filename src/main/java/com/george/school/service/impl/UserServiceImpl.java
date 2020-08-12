package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.config.ConfigProperties;
import com.george.school.entity.Role;
import com.george.school.entity.User;
import com.george.school.entity.UserRole;
import com.george.school.mapper.UserMapper;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.model.dto.UserRoleDTO;
import com.george.school.model.dto.UserTableDTO;
import com.george.school.model.query.UserListQuery;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.TeacherTreeVo;
import com.george.school.model.vo.UserOrgTreeVO;
import com.george.school.service.IRoleService;
import com.george.school.service.IUserRoleService;
import com.george.school.service.IUserService;
import com.george.school.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final ConfigProperties configProperties;
    private final IRoleService roleService;
    private final IUserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(ConfigProperties configProperties, IRoleService roleService, IUserRoleService userRoleService) {
        this.configProperties = configProperties;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @Override
    public LoginUserDto findUserByName(String username) {
        LoginUserDto user = this.baseMapper.selectUserByUserName(username);
        return user;
    }

    @Override
    public User findByName(String username) {
        User user = this.baseMapper.getByUserName(username);
        return user;
    }

    @Override
    public void updateUserLoginInfo(User user) {
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        user.setLastLoginTime(LocalDateTime.now());
        user.setLoginCount(user.getLoginCount() + 1);
        this.baseMapper.updateById(user);
    }

    @Override
    public PageInfo<UserTableDTO> pageUserList(UserListQuery query) {
        int pageNum = query.getPage() > 0 ? query.getPage() : 1;
        int pageSize = query.getLimit() > 0 ? query.getLimit() : 10;
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<UserTableDTO> list = this.baseMapper.getUserPageList(query);
        PageInfo<UserTableDTO> pageInfo = new PageInfo<>(list);

        list.stream().forEach(n -> {
            String avatar = n.getAvatar();
            if (StringUtils.isNotEmpty(avatar)) {
                avatar = configProperties.getFileServerAddr() + avatar;
                n.setAvatar(avatar);
            }
        });
        return pageInfo;
    }

    @Override
    public Result saveUserData(User user) {
        // 校验，用户名、手机号是否存在
        if (StringUtils.isNotEmpty(user.getMobile())) {
            int count = this.baseMapper.findUserByMobile(user.getMobile());
            if (count > 0 && StringUtils.isEmpty(user.getId())) {
                return new Result(false, StatusCode.ERROR, "该用户手机号已存在");
            }

            count = this.baseMapper.findByUserName(user.getUsername());
            if (count > 0 && StringUtils.isEmpty(user.getId())) {
                return new Result(false, StatusCode.ERROR, "该用户账号已存在");
            }
        }

        if (StringUtils.isEmpty(user.getId())) {
            // 给用户设置密码
            String password = Md5Util.encrypt(user.getUsername().toLowerCase(), user.getUsername());
            user.setPassword(password);
            // 设置注册ip
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            String registerIp = IpUtil.getIpAddr(request);
            user.setRegIp(registerIp);
            // 设置登录次数为0
            user.setLoginCount(0);
        }

        // 处理默认头像的问题
        if (StringUtils.contains(user.getAvatar(), "head_default.jpg")) {
            user.setAvatar(StringPool.EMPTY);
        }

        // 设置头像路径
        if (StringUtils.isNotEmpty(user.getAvatar())) {
            try {
                URI uri = new URI(user.getAvatar());
                String path = uri.getPath();
                user.setAvatar(path);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        this.saveOrUpdate(user);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @Override
    public UserRoleDTO getUserRoleData(String id) {
        // 所有角色
        List<Role> roles = roleService.list();

        // 当前用户具有的角色id
        List<String> ids = roleService.findUserRoleIds(id);

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setAllRole(roles);
        userRoleDTO.setOwnRoles(ids);
        return userRoleDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserRoles(String[] roleIds, String userId) {
        // 删除原有的用户角色关联
        boolean res = roleService.deleteUserRoles(userId);
        if (!res) {
            throw new RuntimeException("保存用户角色失败");
        }

        // 保存新的角色
        List<UserRole> list = Lists.newArrayList();
        for (int i = 0; i < roleIds.length; i++) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleIds[i]);
            list.add(userRole);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            res = userRoleService.saveBatch(list);
            if (!res) {
                throw new RuntimeException("保存用户角色失败");
            }
        }

        return true;
    }

    @Override
    public List<TeacherTreeVo> findTeacherTreeVo(String id) {
        List<TeacherTreeVo> allTeachers = this.baseMapper.findAllTeacherData();
        if (CollectionUtils.isEmpty(allTeachers)) {
            allTeachers = Lists.newArrayList();
        }

        // 将当前id从集合中剔除
        if (StringUtils.isNotEmpty(id)) {
            Iterator<TeacherTreeVo> iterator = allTeachers.iterator();
            while (iterator.hasNext()) {
                TeacherTreeVo vo = iterator.next();
                if (StringUtils.equals(vo.getId(), id)) {
                    iterator.remove();
                }
            }
        }

        TeacherTreeVo rootVo = TeacherTreeVo.builder()
                .id("0")
                .title("所有教师")
                .spread(true)
                .children(allTeachers)
                .build();
        List<TeacherTreeVo> list = new ArrayList<>();
        list.add(rootVo);
        return list;
    }
}
