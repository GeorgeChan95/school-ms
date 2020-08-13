package com.george.school.controller;

import cn.hutool.core.util.ObjectUtil;
import com.george.school.entity.User;
import com.george.school.entity.UserCourse;
import com.george.school.model.dto.GradeEvaluateDTO;
import com.george.school.model.query.GradeEvaluateQuery;
import com.george.school.service.ICourseService;
import com.george.school.service.IUserCourseService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     学生成绩前端控制器
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/13 18:17
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RequestMapping("/api/score")
public class ScoreContrller {
    private final ICourseService courseService;
    private final IUserCourseService userCourseService;

    @Autowired
    public ScoreContrller(ICourseService courseService, IUserCourseService userCourseService) {
        this.courseService = courseService;
        this.userCourseService = userCourseService;
    }

    @ApiOperation("学生成绩核定列表")
    @PostMapping("/list")
    public Result gradeEvaluate(@RequestBody GradeEvaluateQuery query) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        PageInfo<GradeEvaluateDTO> pageInfo;
        // 学生不显示数据
        if (user.getUserType().equals(0)) {
            pageInfo = new PageInfo<>();
        } else if (user.getUserType().equals(1)) { // 老师
            // 仅查看自己院/系/专业下的学生的成绩列表
            pageInfo = courseService.pageGradeEvaluate(user.getId(), query);
        } else { // 管理员
            pageInfo = courseService.pageGradeEvaluate(StringPool.EMPTY, query);
        }
        return new Result(Boolean.TRUE, StatusCode.OK, "查询成功", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 学生成绩保存
     * @param studentId
     * @param courseId
     * @param userCourseId
     * @param score
     * @return
     */
    @ApiOperation("学生成绩保存")
    @PostMapping("/save")
    public Result save(@RequestParam(value = "studentId", required = false) String studentId,
                       @RequestParam(value = "courseId", required = false) String courseId,
                       @RequestParam(value = "userCourseId", required = false) String userCourseId,
                       @RequestParam(value = "courseScore", required = false) Double score) {
        if (StringUtils.isEmpty(studentId) || StringUtils.isEmpty(courseId) || ObjectUtil.isNull(score)) {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        UserCourse userCourse = UserCourse.builder()
                                .id(userCourseId)
                                .userId(studentId)
                                .courseId(courseId)
                                .isTeacher(0)
                                .score(score)
                                .build();
        boolean res = userCourseService.saveOrUpdate(userCourse);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "成绩评定失败");
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }
}
