package com.george.school.controller;

import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import io.swagger.annotations.Api;
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
@Api(value = "index", tags = "请求跳转模块")
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
     * 系统设置，资源管理页面
     * @return
     */
    @GetMapping("/index/sys/menu")
    public String menuView() {
        return "menu/menu_list";
    }

    /**
     * 系统设置，角色管理页面
     * @return
     */
    @GetMapping("/index/sys/role")
    public String roleView() {
        return "role/role_list";
    }

    /**
     * 系统设置，组织管理页面
     * @return
     */
    @GetMapping("/index/sys/organization")
    public String organizationView() {
        return "org/organization_list";
    }

    /**
     * 课程管理页面
     * @return
     */
    @GetMapping("/index/teach/course")
    public String courseView() {
        return "course/course_list";
    }

    /**
     * 成绩评定页面
     * @return
     */
    @GetMapping("/index/score/list")
    public String scoreView() {
        return "score/score_list";
    }

    /**
     * 成绩查询页面
     * @return
     */
    @GetMapping("/index/record/view")
    public String recordView() {
        return "record/record_list";
    }

    /**
     * 学生列表页面
     * @return
     */
    @GetMapping("/index/student/list")
    public String studentView() {
        return "student/student_list";
    }

    /**
     * 用户信息页面
     * @return
     */
    @GetMapping("/index/users/info")
    public String userInfoView() {
        return "user/user_edit";
    }

    /**
     * 用户更新密码页面
     * @return
     */
    @GetMapping("/index/users/password")
    public String userPassword() {
        return "user/password";
    }

    /**
     * 公告管理
     * @return
     */
    @GetMapping("/index/notice/view")
    public String noticeView() {
        return "notice/notice_list";
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
