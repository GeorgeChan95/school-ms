package com.george.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.entity.RoleResources;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色资源关联表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface RoleResourcesMapper extends BaseMapper<RoleResources> {

    int deleteByRoleId(@Param("roleId") String roleId);
}
