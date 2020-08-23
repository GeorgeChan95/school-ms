package com.george.school.service.impl;

import com.george.school.entity.Notice;
import com.george.school.entity.User;
import com.george.school.mapper.NoticeMapper;
import com.george.school.model.query.NoticeQuery;
import com.george.school.model.vo.MainInfoVO;
import com.george.school.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final IUserService userService;

    @Autowired
    public NoticeServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public PageInfo<Notice> pageNoticeData(NoticeQuery query) {
        int pageNum = query.getPage() > 0 ? query.getPage() : 1;
        int pageSize = query.getLimit() > 0 ? query.getLimit() : 10;
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<Notice> list = this.baseMapper.getNoticePage(query);
        PageInfo<Notice> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public MainInfoVO getUserNoticeInfo(User principal) {
        User user = userService.getById(principal.getId());

        List<Notice> notices;
        MainInfoVO infoVO = new MainInfoVO();
        switch (user.getUserType()) {
            case 0://学生
                notices = this.baseMapper.findStudentNoticeInfo();
                break;
            case 1:// 老师
                notices = this.baseMapper.findTeacherNoticeInfo();
                break;
            default:
                notices = this.baseMapper.findManagerNoticeInfo();
                break;
        }
        for (int i = 0; i < notices.size(); i++) {
            Notice notice = notices.get(i);
            if (i == 0) {
                infoVO.setLastNoticeTitle(notice.getName());
                infoVO.setLastNoticeContent(notice.getContent());
            }
            if (i == 1) {
                infoVO.setBeforeNoticeTitle(notice.getName());
                infoVO.setLastNoticeContent(notice.getContent());
            }
        }
        return infoVO;
    }
}
