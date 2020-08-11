package com.george.school.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/11 20:39
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CourseSaveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private String id;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    @NotBlank(message = "课程名称不能为空")
    private String name;

    /**
     * 课程代码
     */
    @ApiModelProperty(value = "课程代码")
    @NotBlank(message = "课程代码不能为空")
    private String code;

    /**
     * 是否发布 0-为未发布  1-已发布
     */
    @ApiModelProperty(value = "发布状态 0-为未发布  1-已发布")
    private Integer publishFlag;

    /**
     * 课程选课时间范围
     */
    @ApiModelProperty(value = "发布状态 0-为未发布  1-已发布")
    @NotBlank(message = "选课时间不能为空")
    private String courseTime;

    /**
     * 选课开始时间
     */
    @ApiModelProperty(value = "选课开始时间")
    private LocalDateTime startTime;

    /**
     * 选课截止时间
     */
    @ApiModelProperty(value = "选课截止时间")
    private LocalDateTime endTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户课程关联Id")
    private String userCourseId;

    /**
     * 教师ID
     */
    @ApiModelProperty(value = "教师ID")
    private String teacherId;

    /**
     * 教师姓名
     */
    @ApiModelProperty(value = "教师姓名")
    private String teacherName;
}
