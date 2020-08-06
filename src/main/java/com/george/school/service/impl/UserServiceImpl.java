package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.config.ConfigProperties;
import com.george.school.entity.User;
import com.george.school.mapper.UserMapper;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.model.query.UserListQuery;
import com.george.school.service.IUserService;
import com.george.school.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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

    @Autowired
    public UserServiceImpl(ConfigProperties configProperties) {
        this.configProperties = configProperties;
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
    public PageInfo<User> pageUserList(UserListQuery query) {
        int pageNum = query.getPageNum() > 0 ? query.getPageNum() : 1;
        int pageSize = query.getPageSize() > 0 ? query.getPageSize() : 10;
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<User> list = this.baseMapper.getUserPageList(query);
        PageInfo<User> pageInfo = new PageInfo<>(list);

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
            String password = Md5Util.encrypt(user.getUsername(), user.getUsername());
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


        // 设置组织关系

//        int res;
//        if (StringUtils.isEmpty(user.getId())) {
//            res = this.baseMapper.updateById(user);
//        } else {
//            res = this.baseMapper.insert(user);
//        }
//        return res > 0 ? new Result(true, StatusCode.OK, "保存成功") : new Result(false, StatusCode.ERROR, "操作失败");
        this.saveOrUpdate(user);
        return new Result(true, StatusCode.OK, "保存成功");
    }
}
