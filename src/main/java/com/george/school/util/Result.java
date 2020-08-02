package com.george.school.util;

import com.george.school.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 是否成功标记
     */
    private Boolean flag;
    /**
     * 返回系统编码
     */
    private Integer code;
    /**
     * 返回描述
     */
    private String message;
    /**
     * 列表分页，数据总数
     */
    private long count;
    /**
     * 数据明细
     */
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

    public Result(Boolean flag, Integer code, String message, int count, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.count = count;
        this.data = data;
    }
}
