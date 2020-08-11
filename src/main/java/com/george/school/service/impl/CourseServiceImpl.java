package com.george.school.service.impl;

import cn.hutool.core.date.DateUtil;
import com.george.school.entity.Course;
import com.george.school.entity.UserCourse;
import com.george.school.mapper.CourseMapper;
import com.george.school.model.dto.CourseListTableDTO;
import com.george.school.model.dto.CourseSaveDto;
import com.george.school.model.query.CourseListQuery;
import com.george.school.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.service.IUserCourseService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    private final IUserCourseService userCourseService;

    @Autowired
    public CourseServiceImpl(IUserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @Override
    public PageInfo<CourseListTableDTO> pageTeacherCourseTableData(CourseListQuery query) {
        int pageNum = query.getPage() > 0 ? query.getPage() : 1;
        int pageSize = query.getLimit() > 0 ? query.getLimit() : 10;
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<CourseListTableDTO> list = this.baseMapper.getTeacherCourseTableList(query);
        PageInfo<CourseListTableDTO> pageInfo = new PageInfo<>(list);

        list.stream().forEach(n -> {
            int nums = this.baseMapper.getCourseStudentNums(n.getId());
            n.setStudentNums(nums);
        });
        return pageInfo;
    }

    @Override
    public PageInfo<CourseListTableDTO> pageStudentCourseTableData(CourseListQuery query) {
        int pageNum = query.getPage() > 0 ? query.getPage() : 1;
        int pageSize = query.getLimit() > 0 ? query.getLimit() : 10;
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<CourseListTableDTO> list = this.baseMapper.getStudentCourseTableList(query);
        PageInfo<CourseListTableDTO> pageInfo = new PageInfo<>(list);

        list.stream().forEach(n -> {
            int nums = this.baseMapper.getCourseStudentNums(n.getId());
            n.setStudentNums(nums);
        });
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCourseData(CourseSaveDto courseSaveDto) {
        String courseTime = courseSaveDto.getCourseTime();
        String startStr = courseTime.split(StringPool.TILDA)[0];
        String endStr = courseTime.split(StringPool.TILDA)[1];

        Course course = Course.builder()
                .id(courseSaveDto.getId())
                .code(courseSaveDto.getCode())
                .name(courseSaveDto.getName())
                .startTime(DateUtil.parseLocalDateTime(startStr))
                .endTime(DateUtil.parseLocalDateTime(endStr))
                .publishFlag(courseSaveDto.getPublishFlag())
                .remark(courseSaveDto.getRemark())
                .build();

        boolean res = this.saveOrUpdate(course);
        if (!res) {
            throw new RuntimeException("课程保存失败！");
        }

        UserCourse userCourse = UserCourse.builder()
                .id(courseSaveDto.getUserCourseId())
                .userId(courseSaveDto.getTeacherId())
                .courseId(course.getId())
                .isTeacher(1)
                .build();
        res = userCourseService.saveOrUpdate(userCourse);
        if (!res) {
            throw new RuntimeException("课程保存失败！");
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteCourseByIds(String[] ids) {
        // 判断课程是否已发布
        boolean res = this.baseMapper.findPublishCourseByIds(ids) > 0 ? true : false;
        if (res) {
            return new Result(false, StatusCode.ERROR, "已发布的课程不能删除，请重新选择");
        }

        // 删除课程
        res = this.removeByIds(Arrays.asList(ids));
        if (!res) {
            throw new RuntimeException("删除失败");
        }

        // 删除教师与课程的关联
        res = userCourseService.removeByCourseIds(ids);
        if (!res) {
            throw new RuntimeException("删除失败");
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
