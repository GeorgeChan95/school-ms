package com.george.school.controller;

import com.george.school.model.query.StudentQuery;
import com.george.school.model.vo.StudentScoreVO;
import com.george.school.model.vo.StudentTableVO;
import com.george.school.service.IUserService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/14 19:02
 * @since JDK 1.8
 */
@Slf4j
@RestController
@Api(value = "student", tags = "学生列表模块")
@RequestMapping("/api/student")
public class StudentController {
    private final IUserService userService;

    @Autowired
    public StudentController(IUserService userService) {
        this.userService = userService;
    }


    @ApiOperation("学生列表")
    @PostMapping("/list")
    public Result pageStudentList(@RequestBody StudentQuery query) {
        PageInfo<StudentTableVO> pageInfo = userService.findStudentPageList(query);
        return new Result(true, StatusCode.OK, "查询成功", (int)pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据学生id，分页获取学生的成绩列表数据
     * @param page 当前页
     * @param limit 页大小
     * @param studentId 学生ID
     * @return
     */
    @ApiOperation("根据学生ID获取学生成绩列表")
    @GetMapping("/scoreList")
    public Result pageStudentScore(@RequestParam (value = "studentId", required = false) String studentId,
                                   @RequestParam (value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam (value = "limit", required = false, defaultValue = "20") Integer limit) {
        PageInfo<StudentScoreVO> pageInfo = userService.findStudentScorePage(page, limit, studentId);
        return new Result(true, StatusCode.OK, "查询成功", (int)pageInfo.getTotal(), pageInfo.getList());
    }
}
