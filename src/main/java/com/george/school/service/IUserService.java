package com.george.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.entity.User;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.model.query.UserListQuery;
import com.george.school.util.Result;
import com.github.pagehelper.PageInfo;

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

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 更新用户登录数据
     * @param user 用户当前信息
     */
    void updateUserLoginInfo(User user);

    /**
     * 获取用户分页数据列表
     * @param query 查询条件
     * @return
     */
    PageInfo<User> pageUserList(UserListQuery query);

    /**
     * 用户数据保存
     * @param user
     * @return
     */
    Result saveUserData(User user);
}
