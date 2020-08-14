package com.george.school.service;

import com.george.school.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.model.query.NoticeQuery;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 校园公告表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-14
 */
public interface INoticeService extends IService<Notice> {

    /**
     * 分页查询公告列表
     * @param query 查询参数列表
     * @return
     */
    PageInfo<Notice> pageNoticeData(NoticeQuery query);
}
