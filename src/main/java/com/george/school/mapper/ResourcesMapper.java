package com.george.school.mapper;

import com.george.school.entity.Resources;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.model.vo.ResourceVO;

import java.util.List;

/**
 * <p>
 * 系统资源表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface ResourcesMapper extends BaseMapper<Resources> {

    /**
     * 获取权限和菜单列表
     * @return 菜单和权限列表数据集合
     */
    List<ResourceVO> selectUrlAndPermision();
}
