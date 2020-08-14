package com.george.school.service.impl;

import com.george.school.entity.Notice;
import com.george.school.mapper.NoticeMapper;
import com.george.school.model.query.NoticeQuery;
import com.george.school.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 校园公告表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-14
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Override
    public PageInfo<Notice> pageNoticeData(NoticeQuery query) {
        int pageNum = query.getPage() > 0 ? query.getPage() : 1;
        int pageSize = query.getLimit() > 0 ? query.getLimit() : 10;
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<Notice> list = this.baseMapper.getNoticePage(query);
        PageInfo<Notice> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
