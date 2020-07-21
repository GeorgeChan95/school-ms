package com.george.school.service.impl;

import com.george.school.entity.UserRole;
import com.george.school.mapper.UserRoleMapper;
import com.george.school.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
