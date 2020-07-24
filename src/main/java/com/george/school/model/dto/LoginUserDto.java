package com.george.school.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 *     用户登录-数据传输对象
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/23 22:09
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户状态 0-正常 1-注销（锁定）
     */
    private int status;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 性别（1-男  2-女）
     */
    private Integer gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 最近登录IP
     */
    private String lastLoginIp;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 用户角色集合
     */
    private Set<String> roles;

    /**
     * 用户权限集合
     */
    private Set<String> perms;
}
