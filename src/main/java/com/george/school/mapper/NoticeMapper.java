package com.george.school.mapper;

import com.george.school.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.model.query.NoticeQuery;
import com.george.school.model.vo.MainInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 校园公告表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-08-14
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 分页获取公告数据列表
     * @param query 参数列表
     * @return
     */
    List<Notice> getNoticePage(@Param("query") NoticeQuery query);

    /**
     * 获取学生查看的公告信息
     * @return
     */
    List<Notice> findStudentNoticeInfo();

    /**
     * 获取老师查看的公告信息
     * @return
     */
    List<Notice> findTeacherNoticeInfo();

    /**
     * 获取管理人员查看的公告信息
     * @return
     */
    List<Notice> findManagerNoticeInfo();
}
