package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.entity.Role;
import com.george.school.entity.RoleResources;
import com.george.school.mapper.RoleMapper;
import com.george.school.model.dto.RoleSaveDTO;
import com.george.school.model.query.RoleListQuery;
import com.george.school.service.IRoleResourcesService;
import com.george.school.service.IRoleService;
import com.george.school.util.Result;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    private final IRoleResourcesService roleResourcesService;

    @Autowired
    public RoleServiceImpl(IRoleResourcesService roleResourcesService) {
        this.roleResourcesService = roleResourcesService;
    }

    @Override
    public Set<String> getRolesByUserId(String id) {
        Set<String> roles = this.baseMapper.selectRolesByUserId(id);
        if (CollectionUtils.isEmpty(roles)) {
            roles = Sets.newHashSet();
        }
        return roles;
    }

    @Override
    public PageInfo<Role> pageRoleData(RoleListQuery query) {
        int pageNum = query.getPage() > 0 ? query.getPage() : 1;
        int pageSize = query.getLimit() > 0 ? query.getLimit() : 10;
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<Role> list = this.baseMapper.getRolePageList(query);
        PageInfo<Role> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(RoleSaveDTO role) {
        List<String> permissionIds = role.getPermissionIds();

        Role data = Role.builder()
                .id(StringUtils.isEmpty(role.getId()) ? StringPool.EMPTY : role.getId())
                .code(role.getCode())
                .name(role.getName())
                .remark(StringUtils.isEmpty(role.getRemark()) ? StringPool.EMPTY : role.getRemark())
                .build();
        boolean res = this.saveOrUpdate(data);
        if (!res) {
            throw new RuntimeException("角色保存失败！");
        }

        List<RoleResources> resources = Lists.newArrayList();
        permissionIds.forEach(n -> {
            RoleResources roleResources = new RoleResources();
            roleResources.setRoleId(data.getId());
            roleResources.setResourcesId(n);
            resources.add(roleResources);
        });

        // 删除该角色有的角色关系
        res = roleResourcesService.deleteByRoleId(data.getId());
        if (!res) {
            throw new RuntimeException("角色保存失败！");
        }

        // 角色角色资源关系
        res = roleResourcesService.saveBatch(resources);
        if (!res) {
            throw new RuntimeException("角色保存失败！");
        }
        return res;
    }

    @Override
    public void deleteResourceRole(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            roleResourcesService.deleteByRoleId(ids[i]);
        }
    }

    @Override
    public List<String> findUserRoleIds(String id) {
        List<String> roleIds = this.baseMapper.listUserRoleIds(id);
        if(CollectionUtils.isEmpty(roleIds)) {
            roleIds = Lists.newArrayList();
        }
        return roleIds;
    }

    @Override
    public boolean deleteUserRoles(String userId) {
        int res = this.baseMapper.deleteUserRoles(userId);
        return res >= 0;
    }
}
