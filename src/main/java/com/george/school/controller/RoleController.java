package com.george.school.controller;


import com.george.school.entity.Role;
import com.george.school.model.dto.RoleSaveDTO;
import com.george.school.model.query.RoleListQuery;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.RoleTreeVO;
import com.george.school.service.IResourcesService;
import com.george.school.service.IRoleService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Slf4j
@RestController
@RequestMapping("/api/role")
@Api(value = "Role", tags = "角色管理模块")
public class RoleController {
    private final IRoleService roleService;
    private final IResourcesService resourcesService;

    @Autowired
    public RoleController(IRoleService roleService, IResourcesService resourcesService) {
        this.roleService = roleService;
        this.resourcesService = resourcesService;
    }

    @ApiOperation("角色列表")
    @PostMapping("/list")
    public Result roleList(@RequestBody RoleListQuery query) {
        PageInfo<Role> pageInfo = roleService.pageRoleData(query);
        return new Result(true, StatusCode.OK, "查询成功", (int)pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 获取角色对应的权限资源树
     * @param id
     * @return
     */
    @ApiOperation("权限资源树")
    @PostMapping("/tree")
    public Result rolePermissionTree(@RequestParam(value = "id", required = false) String id) {
        List<RoleTreeVO> menuTreeVOS = resourcesService.findRolePermission(id);
        return new Result(true, StatusCode.OK, "查询成功", menuTreeVOS);
    }

    /**
     * 角色保存
     * @param role
     * @return
     */
    @ApiOperation("角色保存")
    @PostMapping("/save")
    public Result save(@RequestBody RoleSaveDTO role) {
        boolean res = roleService.saveRole(role);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "保存失败！");
        }
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 角色删除
     * @param ids 角色id集合
     * @return
     */
    @ApiOperation("用户删除")
    @PostMapping("/delete")
    public Result delete(@ApiParam("角色id数组") @RequestParam(value = "ids", required = false) String[] ids) {
        if (ids == null || ids.length == 0) {
            return new Result(false, StatusCode.ERROR, "参数异常！");
        }
        // 删除角色数据
        roleService.removeByIds(Arrays.asList(ids));
        // 删除角色资源关联数据
        roleService.deleteResourceRole(ids);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
