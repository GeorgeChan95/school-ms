package com.george.school.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 返回状态枚举类
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/2/29 12:30
 * @since JDK 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
public enum StatusCodeEnums {
    HTTP_SUCCESS(10001, "发送成功！"),
    HTTP_FAIL(10002, "请确认GCS伺服是否已经启动！");

    @Getter
    private int code;
    @Getter
    private String msg;
}
