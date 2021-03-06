package com.george.school.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.george.school.entity.Course;
import com.george.school.entity.UserCourse;
import com.george.school.mapper.CourseMapper;
import com.george.school.model.dto.CourseListTableDTO;
import com.george.school.model.dto.CourseSaveDto;
import com.george.school.model.dto.GradeEvaluateDTO;
import com.george.school.model.query.CourseListQuery;
import com.george.school.model.query.GradeEvaluateQuery;
import com.george.school.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.george.school.service.IUserCourseService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public boolean publishCourseData(String id) {
        Course course = this.baseMapper.selectById(id);
        course.setPublishFlag(1);
        this.updateById(course);
        return this.updateById(course);
    }

    @Override
    public Result cancelCourseData(String id) {
        // 若课程已有学生选择，则不能取消发布
        boolean res = userCourseService.findByCourseId(id);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "已有学生选择该课程，无法取消");
        }
        Course course = this.baseMapper.selectById(id);
        course.setPublishFlag(0);
        res = this.updateById(course);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

    @Override
    public boolean selectCourse(String id, String courseId) {
        UserCourse course = userCourseService.findDataByUserIdAndCourseId(id, courseId);
        if (ObjectUtil.isNotNull(course)) {
            return false;
        }

        UserCourse userCourse = UserCourse.builder()
                .userId(id)
                .courseId(courseId)
                .isTeacher(0)
                .score(0d)
                .build();
        return userCourseService.save(userCourse);
    }

    @Override
    public boolean unselectCourse(String id, String courseId) {
        UserCourse course = userCourseService.findDataByUserIdAndCourseId(id, courseId);
        if (ObjectUtil.isNull(course)) {
            return true;
        }
        return userCourseService.removeById(course.getId());
    }

    @Override
    public PageInfo<GradeEvaluateDTO> pageGradeEvaluate(String teacherId, GradeEvaluateQuery query) {
        int pageNum = query.getPage() > 0 ? query.getPage() : 1;
        int pageSize = query.getLimit() > 0 ? query.getLimit() : 10;
        if (StringUtils.isNotEmpty(teacherId)) {
            query.setTeacherId(teacherId);
        }
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<GradeEvaluateDTO> list = this.baseMapper.getGradeEvaluateList(query);
        PageInfo<GradeEvaluateDTO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
