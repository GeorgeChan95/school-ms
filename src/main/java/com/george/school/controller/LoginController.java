package com.george.school.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录前端控制器
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 22:27
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public void login(@RequestParam("username") String userName, @RequestParam("password") String Password, @RequestParam("rememberMe") boolean rememberMe) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, Password, rememberMe);
        // token.setRememberMe(rememberMe);// 默认不记住密码
        try {
            currentUser.login(token); //登录
            log.info("==========登录成功=======");
//            return new Result(true, "登录成功")
        } catch (UnknownAccountException e) {
            log.info("==========用户名不存在=======");
//            return new Result(false, "用户名不存在");
        } catch (DisabledAccountException e) {
            log.info("==========您的账户已经被冻结=======");
//            return new Result(false, "您的账户已经被冻结");
        } catch (IncorrectCredentialsException e) {
            log.info("==========密码错误=======");
//            return new Result(false, "密码错误");
        } catch (ExcessiveAttemptsException e) {
            log.info("==========您错误的次数太多了吧,封你半小时=======");
//            return new Result(false, "您错误的次数太多了吧,封你半小时");
        } catch (AccountException e) {
            log.info("==========账号密码错误=======");
        } catch (RuntimeException e) {
            log.info("==========运行异常=======");
//            return new Result(false, "运行异常");
        }
    }

    @RequestMapping("/logout")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index";
    }
}
