package com.george.school.service;

import com.george.school.entity.Organization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.model.dto.OrganizationTableDTO;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.OrganizationTreeVO;
import com.george.school.model.vo.UserOrgTreeVO;
import com.george.school.util.Result;

import java.util.List;

/**
 * <p>
 * 院系组织结构表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
public interface IOrganizationService extends IService<Organization> {

    /**
     * 获取组织结构数据列表
     * @return
     */
    List<OrganizationTableDTO> findOrganizationTableData();

    /**
     * 获取组织结构树
     * @param orgId 组织结构id
     * @return
     */
    List<OrganizationTreeVO> findOrgTree(String orgId);

    /**
     * 用户组织树
     * @return
     */
    List<OrganizationTreeVO> findUserOrgTree();

    /**
     * 组织结构保存
     * @param organization 组织数据
     * @return
     */
    Result saveOrganization(Organization organization);
}
