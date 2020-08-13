package com.george.school.model.vo;

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
 * @date 2020/8/13 23:43
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "StudentTableVO", description = "学生列表对象")
@NoArgsConstructor
@AllArgsConstructor
public class StudentTableVO implements Serializable {
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

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "注册时间")
    private String createTime;

    @ApiModelProperty(value = "状态")
    private int status;

    @ApiModelProperty(value = "上次登陆时间")
    private String lastLoginTime;

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
