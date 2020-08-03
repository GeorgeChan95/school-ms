package com.george.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.entity.User;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.model.query.UserListQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    LoginUserDto selectUserByUserName(@Param("username") String username);

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    User getByUserName(@Param("username") String username);

    /**
     * 获取用户列表的分页数据
     * @param query 查询条件
     * @return
     */
    List<User> getUserPageList(@Param("query") UserListQuery query);

    /**
     * 根据手机号判断用户是否存在
     * @param mobile
     * @return
     */
    int findUserByMobile(@Param("mobile") String mobile);

    /**
     * 根据用户登陆账户判断用户是否已存在
     * @param username
     * @return
     */
    int findByUserName(@Param("username") String username);
}
