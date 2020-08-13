package com.george.school.controller;

import com.george.school.model.dto.GradeEvaluateDTO;
import com.george.school.model.query.GradeEvaluateQuery;
import com.george.school.service.ICourseService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/13 23:02
 * @since JDK 1.8
 */
@Slf4j
@RestController
@Api(value = "record", tags = "学生成绩查询模块")
@RequestMapping("/api/record")
public class RecordController {
    private final ICourseService courseService;

    @Autowired
    public RecordController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @ApiOperation("成绩查询")
    @PostMapping("/list")
    public Result recordPage(@RequestBody GradeEvaluateQuery query) {
        PageInfo<GradeEvaluateDTO> pageInfo;
        pageInfo = courseService.pageGradeEvaluate(StringPool.EMPTY, query);
        return new Result(Boolean.TRUE, StatusCode.OK, "查询成功", (int) pageInfo.getTotal(), pageInfo.getList());
    }
}
