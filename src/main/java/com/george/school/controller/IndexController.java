package com.george.school.controller;

import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * 返回菜单对应的html页面
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/24 10:50
 * @since JDK 1.8
 */
@Controller
public class IndexController {
    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index/home";
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @GetMapping(value = "/index/login")
    public String loginView() {
        return "login";
    }

    /**
     * 跳转到home页面
     *
     * @return
     */
    @GetMapping(value = "/index/home")
    public String homeView() {
        return "home";
    }

    /**
     * 主页面，首页
     *
     * @return
     */
    @GetMapping(value = "/index/main")
    public String mainView() {
        return "main";
    }

    /**
     * 系统设置，用户管理页面
     * @return
     */
    @GetMapping("/index/sys/user")
    public String userView() {
        return "user/user_list";
    }

    /**
     * 未授权的页面
     *
     * @return
     */
    @GetMapping(value = "/index/unauthorized")
    public String unauthorized() {
        return "error/401";
    }

    /**
     * 禁止访问页面，无权限
     * @return
     */
    @GetMapping("/index/403")
    public String error403() {
        return "error/403";
    }

    /**
     * 找不到资源
     * @return
     */
    @GetMapping("/index/404")
    public String error404() {
        return "error/404";
    }

    /**
     * 异常页面
     * @return
     */
    @GetMapping("/index/500")
    public String error405() {
        return "error/500";
    }
}
