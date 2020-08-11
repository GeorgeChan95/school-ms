package com.george.school.service;

import com.george.school.entity.UserCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 人员与课程关联表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
public interface IUserCourseService extends IService<UserCourse> {

    /**
     * 根据课程id删除教师与课程的关联
     * @param ids 课程id数组
     * @return
     */
    boolean removeByCourseIds(String[] ids);
}
