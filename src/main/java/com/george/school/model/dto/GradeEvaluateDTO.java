package com.george.school.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/13 19:03
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "GradeEvaluateDTO", description = "学生成绩判定列表对象")
@NoArgsConstructor
@AllArgsConstructor
public class GradeEvaluateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生ID")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "学号")
    private String username;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "用户课程关联ID")
    private String userCourseId;

    @ApiModelProperty(value = "分数")
    private Double score;

    @ApiModelProperty(value = "课程选课开始时间")
    private String startTime;

    @ApiModelProperty(value = "课程选课结束时间")
    private String endTime;

    @ApiModelProperty(value = "学院ID")
    private String collegeId;

    @ApiModelProperty(value = "学院名称")
    private String collegeName;

    @ApiModelProperty(value = "系ID")
    private String departmentId;

    @ApiModelProperty(value = "系名称")
    private String departmentName;

    @ApiModelProperty(value = "专业ID")
    private String majorId;

    @ApiModelProperty(value = "专业名称")
    private String majorName;
}
