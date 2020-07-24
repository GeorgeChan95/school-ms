package com.george.school.service;

import com.george.school.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据用户id获取用户的所有角色
     * @param id 用户id
     * @return
     */
    Set<String> getRolesByUserId(Long id);
}
