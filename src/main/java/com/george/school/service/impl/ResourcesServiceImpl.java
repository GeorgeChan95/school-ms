package com.george.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.entity.Resources;
import com.george.school.mapper.ResourcesMapper;
import com.george.school.model.vo.HomeResouceVO;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.MenuVO;
import com.george.school.model.vo.RoleTreeVO;
import com.george.school.service.IResourcesService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Iterator;
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
     *
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
     *
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
     *
     * @param resources 菜单资源数据集合
     * @return
     */
    private List<HomeResouceVO> getRootNode(List<HomeResouceVO> resources) {
        List<HomeResouceVO> resouceVOS = Lists.newArrayList();
        resources.stream().forEach(r -> {
            if (StringUtils.equals(r.getParentId(), "0")) {
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
    public List<MenuVO> findResourceTableData(String userId) {
        List<MenuVO> resources = this.baseMapper.listResourceTableData();
        if (CollectionUtils.isEmpty(resources)) {
            resources = Collections.EMPTY_LIST;
        }
        return resources;
    }

    @Override
    public List<MenuTreeVO> findMenuTree(String resourceId) {
        List<MenuTreeVO> resources = this.baseMapper.findAllMenu();
        if (CollectionUtils.isEmpty(resources)) {
            resources = Lists.newArrayList();
        }

        // 将当前资源从集合中剔除
        if (StringUtils.isNotEmpty(resourceId)) {
            Iterator<MenuTreeVO> iterator = resources.iterator();
            while (iterator.hasNext()) {
                MenuTreeVO resouceVO = iterator.next();
                if (StringUtils.equals(resouceVO.getId(), resourceId)) {
                    iterator.remove();
                }
            }
        }

        // 返回的菜单树
        List<MenuTreeVO> resouceVOTree = buildMenuTree(resources);

        return resouceVOTree;
    }

    /**
     * 组建菜单资源树形结构
     *
     * @param resources
     * @return
     */
    private List<MenuTreeVO> buildMenuTree(List<MenuTreeVO> resources) {
        List<MenuTreeVO> trees = Lists.newArrayList();
        for (MenuTreeVO rootNode : getRobotMenuNode(resources)) {
            rootNode = buildChildMenuTree(rootNode, resources);
            trees.add(rootNode);
        }
        return trees;
    }

    /**
     * 获取跟节点数据集合
     *
     * @param resources 菜单资源数据集合
     * @return
     */
    private List<MenuTreeVO> getRobotMenuNode(List<MenuTreeVO> resources) {
        List<MenuTreeVO> resouceVOS = Lists.newArrayList();
        resources.stream().forEach(r -> {
            if (StringUtils.equals(r.getParentId(), "0")) {
                resouceVOS.add(r);
            }
        });
        return resouceVOS;
    }

    /**
     * 为某个跟节点，找到它的子节点
     *
     * @param rootNode
     * @param resources
     * @return
     */
    private MenuTreeVO buildChildMenuTree(MenuTreeVO rootNode, List<MenuTreeVO> resources) {
        List<MenuTreeVO> childTrees = Lists.newArrayList();
        resources.stream().forEach(r -> {
            if (r.getParentId().equals(rootNode.getId())) {
                childTrees.add(buildChildMenuTree(r, resources));
            }
        });
        rootNode.setChildren(childTrees);
        return rootNode;
    }

    @Override
    public Result saveMenuData(Resources resources) {
        boolean res = this.saveOrUpdate(resources);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "保存失败");
        }
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @Override
    public List<RoleTreeVO> findRolePermission(String roleId) {

        List<RoleTreeVO> roleTreeLists = Lists.newArrayList();
        if (StringUtils.isNotEmpty(roleId)) {
            roleTreeLists = this.baseMapper.findRoleMenu(roleId);
        }

        // 获取所有资源树
        List<RoleTreeVO> allTree = this.baseMapper.findAllRoleTree();
        for (RoleTreeVO roleTreeVO : allTree) {
            for (RoleTreeVO treeVO : roleTreeLists) {
                if (StringUtils.equals(roleTreeVO.getId(), treeVO.getId())) {
                    roleTreeVO.setChecked(true);
                }
            }
        }

        // 返回的菜单树
        List<RoleTreeVO> resouceVOTree = buildRoleTree(allTree);
        return resouceVOTree;
    }

    /**
     * 组建菜单资源树形结构
     *
     * @param resources
     * @return
     */
    private List<RoleTreeVO> buildRoleTree(List<RoleTreeVO> resources) {
        List<RoleTreeVO> trees = Lists.newArrayList();
        for (RoleTreeVO rootNode : getRobotRoleNode(resources)) {
            rootNode = buildChildRoleTree(rootNode, resources);
            rootNode.setChecked(false);
            trees.add(rootNode);
        }
        return trees;
    }

    /**
     * 获取跟节点数据集合
     *
     * @param resources 菜单资源数据集合
     * @return
     */
    private List<RoleTreeVO> getRobotRoleNode(List<RoleTreeVO> resources) {
        List<RoleTreeVO> resouceVOS = Lists.newArrayList();
        resources.stream().forEach(r -> {
            if (StringUtils.equals(r.getParentId(), "0")) {
                resouceVOS.add(r);
            }
        });
        return resouceVOS;
    }

    /**
     * 为某个跟节点，找到它的子节点
     *
     * @param rootNode
     * @param resources
     * @return
     */
    private RoleTreeVO buildChildRoleTree(RoleTreeVO rootNode, List<RoleTreeVO> resources) {
        List<RoleTreeVO> childTrees = Lists.newArrayList();
        resources.stream().forEach(r -> {
            if (CollectionUtils.isNotEmpty(r.getChildren())) {
                r.setChecked(false);
            }
            if (r.getParentId().equals(rootNode.getId())) {
                childTrees.add(buildChildRoleTree(r, resources));
            }
        });
        rootNode.setChildren(childTrees);
        return rootNode;
    }
}
