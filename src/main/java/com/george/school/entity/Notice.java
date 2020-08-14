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
 * 校园公告表
 * </p>
 *
 * @author George Chan
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公告名称
     */
    @TableField("name")
    private String name;

    /**
     * 公告内容
     */
    @TableField("content")
    private String content;

    /**
     * 公告对象  1-学生  2-老师  3-全体人员
     */
    @TableField("target")
    private Integer target;

    /**
     * 发布状态 0-未发布  1-已发布
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
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
