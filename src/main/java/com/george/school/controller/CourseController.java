package com.george.school.controller;


import com.alibaba.fastjson.JSON;
import com.george.school.entity.User;
import com.george.school.model.dto.CourseListTableDTO;
import com.george.school.model.dto.CourseSaveDto;
import com.george.school.model.query.CourseListQuery;
import com.george.school.model.vo.TeacherTreeVo;
import com.george.school.service.ICourseService;
import com.george.school.service.IUserService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
@Slf4j
@RestController
@RequestMapping("/api/course")
public class CourseController {
    private final ICourseService courseService;
    private final IUserService userService;

    @Autowired
    public CourseController(ICourseService courseService, IUserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    /**
     * 获取当前用户类型
     * @return
     */
    @ApiOperation("课程列表")
    @GetMapping("/user-type")
    public Result getUserType() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return new Result(true, StatusCode.OK, "获取成功", user.getUserType());
    }

    /**
     * 课程数据列表
     *  管理员：
     *      添加课程
     *      删除课程（仅在课程未发布时）
     *      发布课程
     *      选择任课老师
     *      编辑课程
     *  老师：
     *      添加课程
     *      删除课程（仅在未发布时）
     *      编辑课程
     *      发布课程
     *  学生：
     *      查看列表（已发布的课程）
     *      选课（仅在选课时间范围内）
     *      退课（仅在选课时间范围内）
     *      过滤已选未选
     * @param query
     * @return
     */
    // @RequiresPermissions("sys:course")
    @ApiOperation("课程列表")
    @PostMapping("/list")
    public Result courseList(@RequestBody CourseListQuery query) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        PageInfo<CourseListTableDTO> pageInfo = new PageInfo<>();
        Integer userType = user.getUserType();
        // 如果是学生查看列表
        if (userType.equals(0)) {
            query.setUserId(user.getId());
            pageInfo = courseService.pageStudentCourseTableData(query);
        } else {// 其它用户查看列表
            pageInfo = courseService.pageTeacherCourseTableData(query);
        }
        return new Result(Boolean.TRUE, StatusCode.OK, "查询成功", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 任课教师选择树
     * @param id 任课老师ID
     * @return
     */
    @ApiOperation("教师树")
    @PostMapping("/teacher/tree")
    public Result getTeacherTree(@ApiParam(value = "教师ID") @RequestParam(value = "id", required = false) String id) {
        List<TeacherTreeVo> list = userService.findTeacherTreeVo(id);
        log.info("教师树获取 ===> {}", JSON.toJSONString(list));
        return new Result(true, StatusCode.OK, "操作成功！", list);
    }

    /**
     * 课程对象保存
     * @param courseSaveDto
     * @return
     */
    @ApiOperation("课程保存")
    @PostMapping("/save")
    public Result courseSave(@Valid @RequestBody CourseSaveDto courseSaveDto) {
        boolean res = courseService.saveCourseData(courseSaveDto);
        if (!res) {
            new Result(false, StatusCode.ERROR, "保存失败");
        }
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 课程删除
     *  1、判断课程是否发布，已发布的课程不能删除
     *  2、删除课程
     *  3、删除老师与课程的关联
     *
     * @param ids
     * @return
     */
    @ApiOperation("课程删除")
    @DeleteMapping("/delete")
    public Result deleteCourse(@ApiParam("课程id数组") @RequestParam(value = "ids", required = false) String[] ids) {
        if (ids == null || ids.length == 0) {
            return new Result(false, StatusCode.ERROR, "参数异常！");
        }
        Result result = courseService.deleteCourseByIds(ids);
        return result;
    }

    /**
     * 课程发布
     * @param id 课程id
     * @return
     */
    @ApiOperation("课程发布")
    @GetMapping("/publish")
    public Result publishCourse(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isEmpty(id)) {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        boolean res = courseService.publishCourseData(id);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 课程取消发布
     * @param id 课程id
     * @return
     */
    @ApiOperation("课程取消发布")
    @GetMapping("/cancel")
    public Result cancelCourse(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isEmpty(id)) {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        Result result = courseService.cancelCourseData(id);
        return result;
    }

    /**
     * 课程选择
     * @param id 课程id
     * @return
     */
    @ApiOperation("课程选择")
    @GetMapping("/select")
    public Result selectCourse(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isEmpty(id)) {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        boolean res = courseService.selectCourse(user.getId(), id);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 取消课程选择
     * @param id 课程id
     * @return
     */
    @ApiOperation("取消课程选择")
    @GetMapping("/unselect")
    public Result unselectCourse(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isEmpty(id)) {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        boolean res = courseService.unselectCourse(user.getId(), id);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }
}