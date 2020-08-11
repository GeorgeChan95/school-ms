package com.george.school.mapper;

import com.george.school.entity.UserCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 人员与课程关联表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
public interface UserCourseMapper extends BaseMapper<UserCourse> {

    /**
     * 删除课程与教师的关联
     * @param ids 课程id数组
     * @return
     */
    int deleteByCourseIds(@Param("ids") String[] ids);
}
