package com.george.school.model.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     登录后，主页展示数据对象
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/1 14:47
 * @since JDK 1.8
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户昵称
     */
    private String nickName;
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
     * 菜单资源集合
     */
    List<HomeResouceVO> resouceVOList;
}
