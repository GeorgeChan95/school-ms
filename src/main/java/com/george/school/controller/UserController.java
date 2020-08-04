package com.george.school.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.george.school.config.ConfigProperties;
import com.george.school.entity.User;
import com.george.school.model.query.UserListQuery;
import com.george.school.service.IUserService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        redisTemplate.opsForValue().set("test", 111);
        redisTemplate.opsForValue().set("123", "123");
        return "test";
    }

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public Result userList(@RequestBody UserListQuery query, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
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
}
