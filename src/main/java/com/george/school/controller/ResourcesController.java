package com.george.school.controller;


import com.george.school.entity.Resources;
import com.george.school.entity.User;
import com.george.school.service.IResourcesService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 系统资源表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Slf4j
@RestController
@RequestMapping("/api/menu")
public class ResourcesController {
    private final IResourcesService resourcesService;

    @Autowired
    public ResourcesController(IResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    @ApiOperation("菜单列表")
    @PostMapping("/list")
    public Result menuData() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Resources> resources = resourcesService.findResourceTableData(user.getId());
        return new Result(true, StatusCode.OK, "操作成功", resources);
    }

    /**
     * 获取菜单树
     * @param resourceId 资源id
     * @return
     */
    @ApiOperation("菜单树")
    @PostMapping("/tree")
    public Result menuTree(@RequestParam(value = "resourceId", required = false) String resourceId) {
        return new Result(true, StatusCode.OK, "操作成功！", Collections.EMPTY_LIST);
    }
}
