package com.george.school.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2020-07-18
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @ResponseBody
    @RequiresPermissions("mvn:install")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello World";
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test() {
        return "test";
    }
}
