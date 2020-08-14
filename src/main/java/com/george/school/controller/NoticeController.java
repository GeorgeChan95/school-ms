package com.george.school.controller;


import cn.hutool.core.util.ObjectUtil;
import com.george.school.entity.Notice;
import com.george.school.model.query.NoticeQuery;
import com.george.school.service.INoticeService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * 校园公告表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-08-14
 */
@Slf4j
@RestController
@RequestMapping("/api/notice")
@Api(value = "notice", tags = "校务通知模块")
public class NoticeController {
    private final INoticeService noticeService;

    @Autowired
    public NoticeController(INoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @ApiOperation("公告列表")
    @PostMapping("/list")
    public Result noticePageList(@RequestBody NoticeQuery query) {
        PageInfo<Notice> pageInfo = noticeService.pageNoticeData(query);
        return new Result(true, StatusCode.OK, "查询成功", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    @ApiOperation("公告保存")
    @PostMapping("/save")
    public Result saveNotice(@RequestBody Notice notice) {
        if (ObjectUtil.isNull(notice)) {
            return new Result(false, StatusCode.ERROR, "参数异常，公告保存失败");
        }
        boolean res = noticeService.saveOrUpdate(notice);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "公告保存失败");
        }
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @ApiOperation("公告发布")
    @PostMapping("/release")
    public Result releaseNotice(@RequestBody Notice notice) {
        if (ObjectUtil.isNull(notice)) {
            return new Result(false, StatusCode.ERROR, "参数异常，公告发布失败");
        }
        notice.setReleaseTime(LocalDateTime.now());
        boolean res = noticeService.saveOrUpdate(notice);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "公告保存失败");
        }
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 公告删除
     * @param ids 公告id数组
     * @return
     */
    @ApiOperation("公告删除")
    @PostMapping("/delNotice")
    public Result delNotice(@ApiParam("id数组") @RequestParam(value = "ids", required = false) String[] ids) {
        if (ids == null || ids.length == 0) {
            return new Result(false, StatusCode.ERROR, "参数异常！");
        }
        noticeService.removeByIds(Arrays.asList(ids));
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
