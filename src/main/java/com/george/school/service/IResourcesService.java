package com.george.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.entity.Resources;
import com.george.school.model.vo.HomeResouceVO;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.MenuVO;
import com.george.school.model.vo.RoleTreeVO;
import com.george.school.util.Result;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统资源表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface IResourcesService extends IService<Resources> {

    /**
     * 获取数据库中的菜单和权限列表
     * @return
     */
    List<HomeResouceVO> findUrlAndPermision(String userId);

    /**
     * 根据用户id获取用户的权限信息集合
     * @param id
     * @return
     */
    Set<String> getResourcesByUserId(String id);

    /**
     * 获取菜单权限列表
     * @param userId 当前登录用户id
     * @return
     */
    List<MenuVO> findResourceTableData(String userId);

    /**
     * 获取要选择的菜单树
     * @param resourceId 当前菜单id
     * @return
     */
    List<MenuTreeVO> findMenuTree(String resourceId);

    /**
     * 保存菜单资源
     * @param resources
     * @return
     */
    Result saveMenuData(Resources resources);

    /**
     * 获取角色具有的权限资源树
     * @param roleId
     * @return
     */
    List<RoleTreeVO> findRolePermission(String roleId);
}
