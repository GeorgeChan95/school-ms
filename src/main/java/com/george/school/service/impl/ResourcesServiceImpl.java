package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.entity.Resources;
import com.george.school.mapper.ResourcesMapper;
import com.george.school.model.vo.HomeResouceVO;
import com.george.school.model.vo.ResourceVO;
import com.george.school.service.IResourcesService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    public List<HomeResouceVO> findUrlAndPermision(String userId) {
        List<HomeResouceVO> resources = this.baseMapper.selectUrlAndPermision(userId);
        if (CollectionUtils.isEmpty(resources)) {
            resources = Lists.newArrayList();
        }

        // 返回的菜单树
        List<HomeResouceVO> resouceVOTree = buildResouceTree(resources);

        return resouceVOTree;
    }

    /**
     * 组建菜单资源树形结构
     * @param resources
     * @return
     */
    private List<HomeResouceVO> buildResouceTree(List<HomeResouceVO> resources) {
        List<HomeResouceVO> trees = Lists.newArrayList();
        for (HomeResouceVO rootNode : getRootNode(resources)) {
            rootNode = buildChildTree(rootNode, resources);
            trees.add(rootNode);
        }
        return trees;
    }

    /**
     * 为某个跟节点，找到它的子节点
     * @param rootNode
     * @param resources
     * @return
     */
    private HomeResouceVO buildChildTree(HomeResouceVO rootNode, List<HomeResouceVO> resources) {
        List<HomeResouceVO> childTrees = Lists.newArrayList();
        resources.stream().forEach(r -> {
            if (r.getParentId().equals(rootNode.getId())) {
                childTrees.add(buildChildTree(r, resources));
            }
        });
        rootNode.setChildren(childTrees);
        return rootNode;
    }


    /**
     * 获取跟节点数据集合
     * @param resources 菜单资源数据集合
     * @return
     */
    private List<HomeResouceVO> getRootNode(List<HomeResouceVO> resources) {
        List<HomeResouceVO> resouceVOS = Lists.newArrayList();
        resources.stream().forEach(r -> {
            if (r.getParentId() == 0) {
                resouceVOS.add(r);
            }
        });
        return resouceVOS;
    }


    @Override
    public Set<String> getResourcesByUserId(String id) {
        Set<String> perms = this.baseMapper.selectResourcesByUserId(id);
        if (CollectionUtils.isEmpty(perms)) {
            perms = Sets.newHashSet();
        }
        return perms;
    }

    @Override
    public List<Resources> findResourceTableData(String userId) {
        List<Resources> resources = this.baseMapper.listResourceTableData(userId);
        if (CollectionUtils.isEmpty(resources)) {
            resources = Collections.EMPTY_LIST;
        }
        return resources;
    }
}
