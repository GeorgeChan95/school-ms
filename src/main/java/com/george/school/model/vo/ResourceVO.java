package com.george.school.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *     菜单和权限页面展示对象
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/22 18:52
 * @since JDK 1.8
 */
@Data
@Builder
public class ResourceVO {
    /**
     * 菜单url
     */
    private String url;
    /**
     * 权限配置
     */
    private String permission;
}
