package com.george.school.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    /**
     * 登录用户名
     */
    @TableField("username")
    private String username;

    /**
     * 登录密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 邮箱地址
     */
    @TableField("email")
    private String email;

    /**
     * QQ
     */
    @TableField("qq")
    private String qq;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 性别（1-男  2-女）
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 用户类型（0-学生 1-老师 3-管理员 4-系统管理员）
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 注册IP
     */
    @TableField("reg_ip")
    private String regIp;

    /**
     * 最近登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 最近登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 用户备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 用户状态(0-正常 1-锁定)
     */
    @TableField("status")
    private Integer status;

    /**
     * 所在组织id
     */
    @TableField("org_id")
    private String orgId;

    /**
     * 注册时间
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
