package com.george.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.entity.Role;
import com.george.school.model.query.RoleListQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取用的角色
     * @param userId 用户id
     * @return
     */
    Set<String> selectRolesByUserId(@Param("userId") String userId);

    /**
     * 分页获取角色数据集合
     * @param query 查询条件
     * @return
     */
    List<Role> getRolePageList(@Param("query") RoleListQuery query);
}
