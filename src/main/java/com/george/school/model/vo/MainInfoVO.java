package com.george.school.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/23 15:48
 * @since JDK 1.8
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 上次登录ip
     */
    private String lastLoginIp;
    /**
     * 上次登录时间
     */
    private String lastLoginTime;
    /**
     * 登录次数
     */
    private int loginCount;

    /**
     * 头像
     */
    private String image;

    /**
     * 所属组织名称
     */
    private String orgName;

    /**
     * 最新公告标题
     */
    private String lastNoticeTitle;

    /**
     * 最新公告内容
     */
    private String lastNoticeContent;

    /**
     * 上次公告标题
     */
    private String beforeNoticeTitle;

    /**
     * 上次公告内容
     */
    private String beforeNoticeContent;
}
