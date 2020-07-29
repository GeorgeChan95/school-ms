package com.george.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/29 19:08
 * @since JDK 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ImageTypeEnum {
    GIF("gif"),
    PNG("png");

    @Getter
    private String code;
}
