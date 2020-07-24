package com.george.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/23 23:50
 * @since JDK 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
public enum UserStatusEnum {
    NORMAL(0, "正常"),
    DISABLE(1, "禁用");

    @Getter
    private Integer code;
    @Getter
    private String desc;
}
