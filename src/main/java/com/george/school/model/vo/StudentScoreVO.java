package com.george.school.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/14 20:33
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "StudentScoreVO", description = "学生成绩列表对象")
@NoArgsConstructor
@AllArgsConstructor
public class StudentScoreVO {
    @ApiModelProperty(value = "学生ID")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String nickName;

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
}
