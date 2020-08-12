package com.george.school.service.impl;

import com.george.school.entity.Organization;
import com.george.school.mapper.OrganizationMapper;
import com.george.school.model.dto.OrganizationTableDTO;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.OrganizationTreeVO;
import com.george.school.service.IOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 院系组织结构表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {

    @Override
    public List<OrganizationTableDTO> findOrganizationTableData() {
        List<OrganizationTableDTO> organizations = this.baseMapper.findAllOrganizationData();
        if (CollectionUtils.isEmpty(organizations)) {
            organizations = Collections.EMPTY_LIST;
        }
        return organizations;
    }

    @Override
    public List<OrganizationTreeVO> findOrgTree(String orgId) {
        List<OrganizationTreeVO> organizations = this.baseMapper.findAllOrgTreeData();
        if (CollectionUtils.isEmpty(organizations)) {
            organizations = Collections.EMPTY_LIST;
        }

        // 将当前id从集合中剔除
        if (StringUtils.isNotEmpty(orgId)) {
            Iterator<OrganizationTreeVO> iterator = organizations.iterator();
            while (iterator.hasNext()) {
                OrganizationTreeVO organization = iterator.next();
                if (StringUtils.equals(organization.getId(), orgId)) {
                    iterator.remove();
                }
            }
        }

        // 返回的菜单树
        List<OrganizationTreeVO> resouceVOTree = buildOrgTree(organizations);

        return resouceVOTree;
    }

    @Override
    public List<OrganizationTreeVO> findUserOrgTree() {
        List<OrganizationTreeVO> organizations = this.baseMapper.findAllUserOrgTreeData();
        if (CollectionUtils.isEmpty(organizations)) {
            organizations = Collections.EMPTY_LIST;
        }

        // 返回的树
        List<OrganizationTreeVO> resouceVOTree = buildOrgTree(organizations);

        return resouceVOTree;
    }

    /**
     * 组件组织结构树形结构
     * @param organizations
     * @return
     */
    private List<OrganizationTreeVO> buildOrgTree(List<OrganizationTreeVO> organizations) {
        List<OrganizationTreeVO> trees = Lists.newArrayList();
        for (OrganizationTreeVO rootNode : getRobotNode(organizations)) {
            rootNode = buildChildTree(rootNode, organizations);
            trees.add(rootNode);
        }
        return trees;
    }

    /**
     * 获取跟节点数据集合
     *
     * @param organizations 数据集合
     * @return
     */
    private List<OrganizationTreeVO> getRobotNode(List<OrganizationTreeVO> organizations) {
        List<OrganizationTreeVO> resouceVOS = Lists.newArrayList();
        organizations.stream().forEach(r -> {
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
     * @param organizations
     * @return
     */
    private OrganizationTreeVO buildChildTree(OrganizationTreeVO rootNode, List<OrganizationTreeVO> organizations) {
        List<OrganizationTreeVO> childTrees = Lists.newArrayList();
        organizations.stream().forEach(r -> {
            if (r.getParentId().equals(rootNode.getId())) {
                childTrees.add(buildChildTree(r, organizations));
            }
        });
        rootNode.setChildren(childTrees);
        return rootNode;
    }

    @Override
    public Result saveOrganization(Organization organization) {
        if (organization.getType() == 1 ) {
            organization.setParentId("0");
        }
        String code = organization.getCode();
        if (StringUtils.isEmpty(organization.getId())) {
            int count = this.baseMapper.findOrgByCode(code);
            if (count > 0) {
                return new Result(false, StatusCode.ERROR, "代码重复");
            }
        }

        boolean res = this.saveOrUpdate(organization);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "保存失败");
        }
        return new Result(true, StatusCode.OK, "保存成功");
    }


}
