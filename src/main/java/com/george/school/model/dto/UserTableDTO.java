package com.george.school.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/12 22:19
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTableDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "登录用户名")
    private String username;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String email;

    /**
     * QQ
     */
    @ApiModelProperty(value = "QQ")
    private String qq;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    /**
     * 性别（1-男  2-女）
     */
    @ApiModelProperty(value = "性别（1-男  2-女）")
    private Integer gender;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    /**
     * 用户类型（0-学生 1-老师 3-管理员 4-系统管理员）
     */
    @ApiModelProperty(value = "用户类型（0-学生 1-老师 3-管理员 4-系统管理员）")
    private Integer userType;

    /**
     * 注册IP
     */
    @ApiModelProperty(value = "注册IP")
    private String regIp;

    /**
     * 最近登录IP
     */
    @ApiModelProperty(value = "最近登录IP")
    private String lastLoginIp;

    /**
     * 最近登录时间
     */
    @ApiModelProperty(value = "最近登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 登录次数
     */
    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    /**
     * 用户备注
     */
    @ApiModelProperty(value = "用户备注")
    private String remark;

    /**
     * 所在组织id
     */
    @ApiModelProperty(value = "所在组织id")
    private String orgId;

    /**
     * 所在组织名称
     */
    @ApiModelProperty(value = "所在组织名称")
    private String orgName;

    /**
     * 用户状态(0-正常 1-锁定)
     */
    @ApiModelProperty(value = "用户状态(0-正常 1-锁定)")
    private Integer status;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
