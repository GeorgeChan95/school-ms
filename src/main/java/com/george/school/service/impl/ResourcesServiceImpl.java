package com.george.school.service.impl;

import com.george.school.entity.Resources;
import com.george.school.mapper.ResourcesMapper;
import com.george.school.service.IResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统资源表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements IResourcesService {

}
