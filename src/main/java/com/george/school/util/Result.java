package com.george.school.util;

import lombok.Data;

/**
 * <p>
 *  返回结果对象
 * </p>
 *
 * @author GeorgeChan 2020/2/3 17:24
 * @version 1.0
 * @since jdk1.8
 */
@Data
public class Result {
    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
