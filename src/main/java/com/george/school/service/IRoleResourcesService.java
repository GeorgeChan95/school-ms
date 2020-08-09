package com.george.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.entity.Role;
import com.george.school.entity.RoleResources;

/**
 * <p>
 * 角色资源关联表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface IRoleResourcesService extends IService<RoleResources> {

    /**
     * 根据角色id删除关系数据
     * @param roleId
     */
    boolean deleteByRoleId(String roleId);
}
