package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.entity.User;
import com.george.school.mapper.UserMapper;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.service.IUserService;
import org.springframework.stereotype.Service;

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
}
