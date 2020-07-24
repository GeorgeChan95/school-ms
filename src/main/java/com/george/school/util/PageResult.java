package com.george.school.util;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *  分页对象
 * </p>
 *
 * @author GeorgeChan 2020/2/3 17:26
 * @version 1.0
 * @since jdk1.8
 */
@Data
public class PageResult<T> {
    /**
     * 返回状态标记 true OR false
     */
    private boolean flag;
    /**
     * 返回状态编码
     */
    private Integer code;
    /**
     * 返回状态信息
     */
    private String message;
    /**
     * 总数
     */
    private long recordsTotal;

    private long recordsFiltered;

    private Integer draw;
    /**
     * 行项目集合
     */
    private List<T> data;

    public PageResult() {
    }

    /**
     * 构造函数，保留，不建议使用
     * @param recordsTotal 总数
     * @param data 分页数据
     */
    public PageResult(long recordsTotal, List<T> data) {
        this.recordsFiltered = recordsTotal;
        this.recordsTotal = recordsTotal;
        this.data = data;
    }

    /**
     * 查询失败，没有数据返回时的构造函数
     * @param flag 返回状态标记
     * @param code 返回状态编码
     * @param message 返回状态信息
     */
    public PageResult(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    /**
     * 查询成功，有数据返回时的构造函数
     * @param flag 返回状态标记
     * @param code 返回状态编码
     * @param message 返回状态信息
     * @param recordsTotal 总数量
     * @param data 分页数据
     */
    public PageResult(boolean flag, Integer code, String message, long recordsTotal, List<T> data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
        this.data = data;
    }
}
