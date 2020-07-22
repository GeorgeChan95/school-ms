package com.george.school.service.impl;

import com.george.school.entity.Resources;
import com.george.school.mapper.ResourcesMapper;
import com.george.school.model.vo.ResourceVO;
import com.george.school.service.IResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ResourceVO> findUrlAndPermision() {
        List<ResourceVO> resources = this.baseMapper.selectUrlAndPermision();
        if (CollectionUtils.isEmpty(resources)) {
            resources = Lists.newArrayList();
        }
        return resources;
    }
}
