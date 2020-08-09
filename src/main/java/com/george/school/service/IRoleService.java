package com.george.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.entity.Role;
import com.george.school.model.dto.RoleSaveDTO;
import com.george.school.model.query.RoleListQuery;
import com.george.school.util.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;
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
    Set<String> getRolesByUserId(String id);

    /**
     * 分页获取role数据集合
     * @param query 查询条件
     * @return
     */
    PageInfo<Role> pageRoleData(RoleListQuery query);

    /**
     * 角色保存
     * @param role 保存数据对象
     * @return
     */
    boolean saveRole(RoleSaveDTO role);

    /**
     * 删除角色资源关联数据
     * @param ids
     */
    void deleteResourceRole(String[] ids);

    /**
     * 获取用户的额角色id集合
     * @param id 用户id
     * @return
     */
    List<String> findUserRoleIds(String id);

    /**
     * 删除用户角色关联
     * @param userId
     * @return
     */
    boolean deleteUserRoles(String userId);
}
