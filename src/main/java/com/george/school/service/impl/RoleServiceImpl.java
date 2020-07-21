package com.george.school.service.impl;

import com.george.school.entity.Role;
import com.george.school.mapper.RoleMapper;
import com.george.school.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
