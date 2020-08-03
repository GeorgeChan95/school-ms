package com.george.school.controller;


import cn.hutool.core.util.ObjectUtil;
import com.george.school.entity.User;
import com.george.school.model.query.UserListQuery;
import com.george.school.service.IUserService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    public UserController(RedisTemplate redisTemplate, IUserService userService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
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
}
