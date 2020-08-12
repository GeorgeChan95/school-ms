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

    /**
     * 获取该课程的选课人数
     * @param id 课程id
     * @return
     */
    boolean findByCourseId(String id);

    /**
     * 根据用户id和课程id获取数据
     * @param userId 用户id
     * @param courseId 课程ID
     * @return
     */
    UserCourse findDataByUserIdAndCourseId(String userId, String courseId);
}
