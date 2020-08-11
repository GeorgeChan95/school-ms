package com.george.school.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/10 22:31
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CourseListTableDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程代码
     */
    private String code;

    /**
     * 是否发布 0-为未发布  1-已发布
     */
    private Integer publishFlag;

    /**
     * 选课开始时间
     */
    private LocalDateTime startTime;

    /**
     * 选课截止时间
     */
    private LocalDateTime endTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "用户课程关联Id")
    private String userCourseId;

    /**
     * 任课老师ID
     */
    private String teacherId;
    /**
     * 任课老师姓名
     */
    private String teacherName;
    /**
     * 学生id
     */
    private String studentId;
    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 选课人数
     */
    private int studentNums;
}
