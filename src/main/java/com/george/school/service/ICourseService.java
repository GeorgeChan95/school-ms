package com.george.school.service;

import com.george.school.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.george.school.model.dto.CourseListTableDTO;
import com.george.school.model.dto.CourseSaveDto;
import com.george.school.model.query.CourseListQuery;
import com.george.school.util.Result;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
public interface ICourseService extends IService<Course> {

    /**
     * 获取老师等用户类型下的课程数据列表
     * @param query 参数列表
     * @return
     */
    PageInfo<CourseListTableDTO> pageTeacherCourseTableData(CourseListQuery query);

    /**
     * 分页获取学生类型下的课程数据列表
     * @param query 参数列表
     * @return
     */
    PageInfo<CourseListTableDTO> pageStudentCourseTableData(CourseListQuery query);

    /**
     * 保存课程数据
     * @param courseSaveDto 课程数据对象
     * @return
     */
    boolean saveCourseData(CourseSaveDto courseSaveDto);

    /**
     * 课程删除
     * @param ids 课程id集合
     */
    Result deleteCourseByIds(String[] ids);

    /**
     * 课程发布
     * @param id 课程id
     * @return
     */
    boolean publishCourseData(String id);

    /**
     * 取消课程发布
     * @param id 课程id
     * @return
     */
    Result cancelCourseData(String id);

    /**
     * 课程选择
     * @param id 当前用户id
     * @param courseId 课程ID
     * @return
     */
    boolean selectCourse(String id, String courseId);

    /**
     * 取消课程选择
     * @param id 当前用户id
     * @param courseId 课程ID
     * @return
     */
    boolean unselectCourse(String id, String courseId);
}
