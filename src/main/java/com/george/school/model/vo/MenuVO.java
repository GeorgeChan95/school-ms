package com.george.school.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 *     菜单Table页面展示对象
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/7 20:09
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源类型（1-菜单  2-按钮）
     */
    private Integer type;

    /**
     * 资源url
     */
    private String url;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 父节点id
     */
    private String pid;

    /**
     * 父节点名称
     */
    private String parentName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否外部链接（0-否  1-是）
     */
    private Integer external;

    /**
     * 状态（0-启动  2-禁用）
     */
    private Integer status;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
