package com.george.school.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.george.school.entity.Organization;
import com.george.school.entity.Resources;
import com.george.school.model.dto.OrganizationTableDTO;
import com.george.school.model.vo.MenuTreeVO;
import com.george.school.model.vo.OrganizationTreeVO;
import com.george.school.service.IOrganizationService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 院系组织结构表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
@Slf4j
@RestController
@RequestMapping("/api/org")
public class OrganizationController {
    private final IOrganizationService organizationService;

    @Autowired
    public OrganizationController(IOrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @ApiOperation("院系组织列表")
    @PostMapping("/list")
    public Result organizationData() {
        List<OrganizationTableDTO> list = organizationService.findOrganizationTableData();
        return new Result(false, StatusCode.OK, "操作成功", list);
    }

    /**
     * 获取组织结构树
     *
     * @param orgId 组织结构id
     * @return
     */
    @ApiOperation("组织结构树")
    @PostMapping("/tree")
    public Result menuTree(@RequestParam(value = "orgId", required = false) String orgId) {
        List<OrganizationTreeVO> list = organizationService.findOrgTree(orgId);
        log.info("组织结构树获取 ===> {}", JSON.toJSONString(list));
        return new Result(true, StatusCode.OK, "操作成功！", list);
    }

    /**
     * 组织保存
     *
     * @param organization 保存的组织对象
     * @return
     */
    @ApiOperation("组织保存")
    @PostMapping("/save")
    public Result saveOrg(@RequestBody Organization organization) {
        if (ObjectUtil.isNull(organization)) {
            return new Result(false, StatusCode.ERROR, "参数异常，保存失败");
        }
        Result result = organizationService.saveOrganization(organization);
        return result;

    }

    /**
     * 组织删除
     *
     * @param ids 组织id集合
     * @return
     */
    @ApiOperation("组织结构删除")
    @DeleteMapping("/del")
    public Result delUser(@ApiParam("组织id数组") @RequestParam(value = "ids", required = false) String[] ids) {
        if (ids == null || ids.length == 0) {
            return new Result(false, StatusCode.ERROR, "参数异常！");
        }
        organizationService.removeByIds(Arrays.asList(ids));
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 获取组织数据
     * @param type 类型 1院  2系  3专业
     * @return
     */
    @ApiOperation("获取组织数据")
    @GetMapping("/get")
    public Result getOrgData(@RequestParam(value = "type", required = false, defaultValue = "1") Integer type,
                             @RequestParam(value = "id", required = false) String id) {
        List<Organization> list = organizationService.findByType(type, id);
        return new Result(true, StatusCode.OK, "操作成功", list);
    }
}
