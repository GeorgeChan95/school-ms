package com.george.school.service.impl;

import com.george.school.entity.Notice;
import com.george.school.mapper.NoticeMapper;
import com.george.school.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
