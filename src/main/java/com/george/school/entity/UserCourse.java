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
 * 人员与课程关联表
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_course")
public class UserCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 课程ID
     */
    @TableField("course_id")
    private String courseId;

    /**
     * 是否是老师 0-否  1-是
     */
    @TableField("is_teacher")
    private Integer isTeacher;

    /**
     * 分数
     */
    @TableField("score")
    private Double score;

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
