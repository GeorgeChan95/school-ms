package com.george.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.entity.Resources;
import com.george.school.model.vo.HomeResouceVO;
import com.george.school.model.vo.ResourceVO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统资源表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface IResourcesService extends IService<Resources> {

    /**
     * 获取数据库中的菜单和权限列表
     * @return
     */
    List<HomeResouceVO> findUrlAndPermision(Long userId);

    /**
     * 根据用户id获取用户的权限信息集合
     * @param id
     * @return
     */
    Set<String> getResourcesByUserId(Long id);
}
