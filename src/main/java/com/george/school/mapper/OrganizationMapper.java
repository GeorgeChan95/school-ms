package com.george.school.mapper;

import com.george.school.entity.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.model.dto.OrganizationTableDTO;
import com.george.school.model.vo.OrganizationTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 院系组织结构表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 获取所有的院系组织数据
     * @return
     */
    List<OrganizationTableDTO> findAllOrganizationData();

    /**
     * 获取院系数据集合
     * @return
     */
    List<OrganizationTreeVO> findAllOrgTreeData();

    /**
     * 根据院系代码查询是否存在
     * @param code 代码
     * @return
     */
    int findOrgByCode(@Param("code") String code);
}
