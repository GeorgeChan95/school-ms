package com.george.school.mapper;

import com.george.school.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.model.dto.LoginUserDto;
import org.apache.ibatis.annotations.Param;

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
}
