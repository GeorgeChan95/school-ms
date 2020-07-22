package com.george.school.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * <p>
 *     定义权限缓存相关的缓存key
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/22 19:15
 * @since JDK 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
public enum PermissionKeyEnums {
    ALL_PERMISSION("all_permission", "所有资源权限")
    ;
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
