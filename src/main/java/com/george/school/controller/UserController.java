package com.george.school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final RedisTemplate redisTemplate;

    @Autowired
    public UserController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        redisTemplate.opsForValue().set("test", 111);
        redisTemplate.opsForValue().set("123", "123");
        return "test";
    }

}
