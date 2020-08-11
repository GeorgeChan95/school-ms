package com.george.school.mapper;

import com.george.school.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.model.dto.CourseListTableDTO;
import com.george.school.model.query.CourseListQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 获取教师等用户类型下的课程列表
     * @param query
     * @return
     */
    List<CourseListTableDTO> getTeacherCourseTableList(@Param("query") CourseListQuery query);

    /**
     * 获取课程的选课人数
     * @param id 课程ID
     * @return
     */
    int getCourseStudentNums(@Param("id") String id);

    /**
     * 分页获取学生类型下的课程数据集合
     * @param query 参数列表
     * @return
     */
    List<CourseListTableDTO> getStudentCourseTableList(@Param("query") CourseListQuery query);

    /**
     * 查询发布的课程数量
     * @param ids 课程id数组
     * @return
     */
    int findPublishCourseByIds(@Param("ids") String[] ids);
}
