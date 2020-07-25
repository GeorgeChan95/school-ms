package com.george.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
@RestController
@RequestMapping("/index")
public class IndexController {

    /**
     * 跳转登录页面
     *
     * @param modelAndView
     * @return
     */
    @GetMapping(value = "/login")
    public ModelAndView loginView(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
