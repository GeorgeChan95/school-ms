package com.george.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.entity.Resources;
import com.george.school.model.vo.HomeResouceVO;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.MenuVO;
import com.george.school.model.vo.RoleTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统资源表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface ResourcesMapper extends BaseMapper<Resources> {

    /**
     * 获取权限和菜单列表
     * @return 菜单和权限列表数据集合
     */
    List<HomeResouceVO> selectUrlAndPermision(@Param("userId") String userId);

    /**
     * 根据用户id获取用户的权限集合
     * @param userId 用户id
     * @return
     */
    Set<String> selectResourcesByUserId(@Param("userId") String userId);

    /**
     * 获取用户的菜单资源数据集合
     * @return
     */
    List<MenuVO> listResourceTableData();

    /**
     * 获取所有菜单资源
     * @return
     */
    List<MenuTreeVO> findAllMenu();

    /**
     * 获取角色具有的权限资源
     * @param roleId 角色ID
     * @return
     */
    List<RoleTreeVO> findRoleMenu(@Param("roleId") String roleId);

    /**
     * 获取所有的资源树
     * @return
     */
    List<RoleTreeVO> findAllRoleTree();
}
