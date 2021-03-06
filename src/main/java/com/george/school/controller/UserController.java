package com.george.school.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.george.school.config.ConfigProperties;
import com.george.school.entity.User;
import com.george.school.model.dto.UserRoleDTO;
import com.george.school.model.dto.UserTableDTO;
import com.george.school.model.query.UserListQuery;
import com.george.school.model.vo.OrganizationTreeVO;
import com.george.school.model.vo.UserOrgTreeVO;
import com.george.school.service.IOrganizationService;
import com.george.school.service.IUserService;
import com.george.school.util.Md5Util;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(value = "User", tags = "用户管理模块")
public class UserController {
    private final RedisTemplate<String, Object> redisTemplate;
    private final IUserService userService;
    private final ConfigProperties configProperties;
    private final IOrganizationService organizationService;


    @Autowired
    public UserController(RedisTemplate redisTemplate, IUserService userService, ConfigProperties configProperties, IOrganizationService organizationService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.configProperties = configProperties;
        this.organizationService = organizationService;
    }

    @ApiOperation("用户列表")
    @PostMapping("/list")
    @RequiresPermissions("sys:user")
    public Result userList(@RequestBody UserListQuery query) {
        PageInfo<UserTableDTO> pageInfo = userService.pageUserList(query);
        return new Result(Boolean.TRUE, StatusCode.OK, "操作成功！", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    @ApiOperation("用户保存")
    @PostMapping("/save")
    public Result userSave(@RequestBody User user) {
        if (ObjectUtil.isNull(user)) {
            return new Result(false, StatusCode.ERROR, "参数异常，用户保存失败");
        }
        Result result = userService.saveUserData(user);
        return result;
    }

    @ApiOperation("用户头像上传")
    @PostMapping("/upload")
    public Result userImageUpload(@RequestParam("file") MultipartFile file , HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        String name = file.getName();
        // 文件后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf(StringPool.DOT));
        // 文上传的服务器文件夹
        String headImagePath = configProperties.getHeadImagePath();
        // 图片名称（随机字符串拼上时间戳）
        String imageName = RandomUtil.randomString(30) + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + substring;
        // 图片查看路径
        String imageUrl = configProperties.getHeadUrlPrefix() + StringPool.SLASH + imageName;
        File imageFile = null;
        try {
            imageFile = FileUtil.writeBytes(file.getBytes(), headImagePath + StringPool.SLASH + imageName);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传用户头像失败 ====> {}", e);
            return new Result(false, StatusCode.ERROR, "上传失败");
        }
        return new Result(true, StatusCode.OK, "上传成功", imageUrl);
    }

    /**
     * 用户删除
     * @param userIds 用户id集合
     * @return
     */
    @ApiOperation("用户删除")
    @PostMapping("/delUser")
    public Result delUser(@ApiParam("用户id数组") @RequestParam(value = "userIds", required = false) String[] userIds) {
        if (userIds == null || userIds.length == 0) {
            return new Result(false, StatusCode.ERROR, "参数异常！");
        }
        userService.removeByIds(Arrays.asList(userIds));
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation("用户重置密码")
    @PostMapping("/reset")
    public Result resetUser(@ApiParam("用户id") @RequestParam(value = "id") String id) {
        User user = userService.getById(id);
        if (ObjectUtil.isNull(user)) {
            log.error("系统未找到该用户信息");
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        String password = Md5Util.encrypt(user.getUsername().toLowerCase(), user.getUsername());
        user.setPassword(password);
        userService.updateById(user);
        return new Result(true, StatusCode.OK, "重置用户密码成功");
    }

    @ApiOperation("获取用户角色")
    @GetMapping("/roles/{id}")
    public Result userRoles(@ApiParam("用户id") @PathVariable(value = "id")String id) {
        if (StringUtils.isEmpty(id)) {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        UserRoleDTO roleDTO = userService.getUserRoleData(id);
        return new Result(true, StatusCode.OK, "获取成功", roleDTO);
    }

    /**
     * 保存用户角色数据
     * @param roleIds
     * @return
     */
    @ApiOperation("保存用户角色数据信息")
    @PostMapping("/roles/{userId}")
    public Result saveRoles(@ApiParam("用户角色id数组") @RequestParam(value = "roleIds", required = false) String[] roleIds, @ApiParam("用户id") @PathVariable(value = "userId")String userId) {
        boolean res = userService.saveUserRoles(roleIds, userId);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 获取用户组织树
     * @return
     */
    @ApiOperation("用户组织树")
    @PostMapping("/org/tree")
    public Result getUserOrgTree() {
        List<OrganizationTreeVO> list = organizationService.findUserOrgTree();
        log.info("用户组织树 ===> {}", JSON.toJSONString(list));
        return new Result(true, StatusCode.OK, "操作成功", list);
    }

    @ApiOperation("个人信息")
    @GetMapping("/info")
    public Result userInfo() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        UserTableDTO userDTO = userService.findUserInfo(user.getId());
        return new Result(true, StatusCode.OK, "获取个人信息成功", userDTO);
    }

    /**
     * 用户密码更新
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @ApiOperation("密码更新")
    @PostMapping("/password")
    public Result changePassword(@RequestParam (value = "oldPwd", required = false) String oldPwd,
                                 @RequestParam (value = "newPwd", required = false) String newPwd) {
        // 参数校验
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }

        // 获取当前登录用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        // 校验旧密码输入是否正确
        String oldPassword = user.getPassword();
        oldPwd = Md5Util.encrypt(user.getUsername().toLowerCase(), oldPwd);
        if (!StringUtils.equalsIgnoreCase(oldPassword, oldPwd)) {
            return new Result(false, StatusCode.ERROR, "旧密码输入错误");
        }

        // 新密码更新
        newPwd = Md5Util.encrypt(user.getUsername().toLowerCase(), newPwd);
        user.setPassword(newPwd);
        boolean res = userService.updateById(user);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        return new Result(true, StatusCode.OK, "更新成功");
    }
}
