package com.george.school.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}
