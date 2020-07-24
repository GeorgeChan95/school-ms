package com.george.school.service;

import com.george.school.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.model.dto.LoginUserDto;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return
     */
    LoginUserDto findUserByName(String username);
}
