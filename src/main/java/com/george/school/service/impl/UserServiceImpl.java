package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.entity.User;
import com.george.school.mapper.UserMapper;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.model.query.UserListQuery;
import com.george.school.service.IUserService;
import com.george.school.util.HttpContextUtil;
import com.george.school.util.IpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        return pageInfo;
    }
}
