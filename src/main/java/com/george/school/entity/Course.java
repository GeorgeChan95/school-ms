package com.george.school.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("sys_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 课程名称
     */
    @TableField("name")
    private String name;

    /**
     * 课程代码
     */
    @TableField("code")
    private String code;

    /**
     * 是否发布 0-为未发布  1-已发布
     */
    @TableField("publish_flag")
    private Integer publishFlag;

    /**
     * 选课开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 选课截止时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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
