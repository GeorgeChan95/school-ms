package com.george.school.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统资源表
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resources")
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 资源类型（1-菜单  2-按钮）
     */
    @TableField("type")
    private Integer type;

    /**
     * 资源url
     */
    @TableField("url")
    private String url;

    /**
     * 权限标识
     */
    @TableField("permission")
    private String permission;

    /**
     * 父节点id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 是否外部链接（0-否  1-是）
     */
    @TableField("external")
    private Integer external;

    /**
     * 状态（0-启动  2-禁用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @TableField("delete_flag")
    private Integer deleteFlag;


}
