package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.entity.RoleResources;
import com.george.school.mapper.RoleResourcesMapper;
import com.george.school.service.IRoleResourcesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源关联表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Service
public class RoleResourcesServiceImpl extends ServiceImpl<RoleResourcesMapper, RoleResources> implements IRoleResourcesService {

    @Override
    public boolean deleteByRoleId(String roleId) {
        int res = this.baseMapper.deleteByRoleId(roleId);
        return res >= 0;
    }
}
