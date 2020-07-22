package com.george.school.service;

import java.util.Map;

/**
 * <p>
 *     权限的加载，更新，刷新
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/22 16:52
 * @since JDK 1.8
 */
public interface ShiroService {
    /**
     * 获取数据库中的权限资源配置
     * @return
     */
    Map<String, String> loadFilterChainDefinitions();
}
