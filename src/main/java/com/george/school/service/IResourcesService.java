package com.george.school.service;

import com.george.school.entity.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.model.vo.ResourceVO;

import java.util.List;

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
    List<ResourceVO> findUrlAndPermision();
}
