package com.george.school.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.george.school.entity.Resources;
import com.george.school.entity.User;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.MenuVO;
import com.george.school.service.IResourcesService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@Api(value = "Resources", tags = "菜单资源管理模块")
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
        List<MenuVO> resources = resourcesService.findResourceTableData(user.getId());
        return new Result(true, StatusCode.OK, "操作成功", resources);
    }

    /**
     * 获取菜单树
     *
     * @param resourceId 资源id
     * @return
     */
    @ApiOperation("菜单树")
    @PostMapping("/tree")
    public Result menuTree(@RequestParam(value = "resourceId", required = false) String resourceId) {
        List<MenuTreeVO> list = resourcesService.findMenuTree(resourceId);
        log.info("菜单树获取 ===> {}", JSON.toJSONString(list));
        return new Result(true, StatusCode.OK, "操作成功！", list);
    }

    /**
     * 菜单保存
     *
     * @param resources 保存的资源对象
     * @return
     */
    @ApiOperation("菜单保存")
    @PostMapping("/save")
    public Result saveMenu(@RequestBody Resources resources) {
        if (ObjectUtil.isNull(resources)) {
            return new Result(false, StatusCode.ERROR, "参数异常，保存失败");
        }
        Result result = resourcesService.saveMenuData(resources);
        return result;
    }

    /**
     * 菜单资源删除
     *
     * @param menuIds 资源id集合
     * @return
     */
    @ApiOperation("菜单资源删除")
    @DeleteMapping("/del")
    public Result delUser(@ApiParam("资源id数组") @RequestParam(value = "menuIds", required = false) String[] menuIds) {
        if (menuIds == null || menuIds.length == 0) {
            return new Result(false, StatusCode.ERROR, "参数异常！");
        }
        resourcesService.removeByIds(Arrays.asList(menuIds));
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
