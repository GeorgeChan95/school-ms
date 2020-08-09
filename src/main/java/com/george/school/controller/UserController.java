package com.george.school.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.george.school.config.ConfigProperties;
import com.george.school.entity.User;
import com.george.school.model.dto.UserRoleDTO;
import com.george.school.model.query.UserListQuery;
import com.george.school.service.IUserService;
import com.george.school.util.Md5Util;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
public class UserController {
    private final RedisTemplate<String, Object> redisTemplate;
    private final IUserService userService;
    private final ConfigProperties configProperties;


    @Autowired
    public UserController(RedisTemplate redisTemplate, IUserService userService, ConfigProperties configProperties) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.configProperties = configProperties;
    }

    @ApiOperation("用户列表")
    @PostMapping("/list")
    @RequiresPermissions("sys:user")
    public Result userList(@RequestBody UserListQuery query) {
        PageInfo<User> pageInfo = userService.pageUserList(query);
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

    @ApiOperation("用户重置密码")
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
    @ApiOperation("报错用户角色数据信息")
    @PostMapping("/roles/{userId}")
    public Result saveRoles(@ApiParam("用户角色id数组") @RequestParam(value = "roleIds", required = false) String[] roleIds, @ApiParam("用户id") @PathVariable(value = "userId")String userId) {
        boolean res = userService.saveUserRoles(roleIds, userId);
        if (!res) {
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

}
