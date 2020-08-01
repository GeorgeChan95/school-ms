package com.george.school.controller;

import com.george.school.util.Md5Util;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.ValidateCodeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
@RequiredArgsConstructor
public class LoginController {
    private final RedisTemplate redisTemplate;
    private final ValidateCodeService validateCodeService;

    /**
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @param captchCode 验证码
     * @param rememberMe 是否记住我
     * @param request 请求
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(
            @ApiParam("登录用户名") @RequestParam("username") String userName,
            @ApiParam("登录密码") @RequestParam("password") String password,
            @ApiParam("图片校验码") @RequestParam("captchCode") String captchCode,
            @ApiParam("是否记住密码") @RequestParam(value = "rememberMe", required = false) boolean rememberMe,
            HttpServletRequest request) {
        // 获取登录session
        HttpSession session = request.getSession();
        // 校验验证码
        validateCodeService.check(session.getId(), captchCode);
        // 将密码进行md5加密，目的为了在登录时与数据库密码比对（md5加密不可逆）
        password = Md5Util.encrypt(userName.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, rememberMe);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token); //登录
            log.info("==========登录成功=======");
            return new Result(true, StatusCode.OK, "登录成功");
        } catch (UnknownAccountException e) {
            log.info("==========用户名不存在=======");
            return new Result(false, StatusCode.LOGINERROR, "用户名不存在");
        } catch (DisabledAccountException e) {
            log.info("==========您的账户已经被冻结=======");
            return new Result(false, StatusCode.LOGINERROR, "您的账户已经被冻结");
        } catch (IncorrectCredentialsException e) {
            log.info("==========密码错误=======");
            return new Result(false, StatusCode.LOGINERROR, "密码错误");
        } catch (ExcessiveAttemptsException e) {
            log.info("==========您错误的次数太多了吧,封你半小时=======");
            return new Result(false, StatusCode.LOGINERROR, "您错误的次数太多了吧,封你半小时");
        } catch (AccountException e) {
            return new Result(false, StatusCode.LOGINERROR, "账号密码错误");
        } catch (RuntimeException e) {
            log.info("==========运行异常=======");
            return new Result(false, StatusCode.LOGINERROR, "运行异常");
        }
    }

    /**
     * 获取图片验证码
     * @param request 请求
     * @param response 响应
     */
    @ApiOperation("创建图片验证码")
    @GetMapping("/image/captcha")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        validateCodeService.create(request, response);
    }

    /**
     * 退出登录-shiro配置退出登录的url，此接口可以不写。
     * @return
     */
    @GetMapping("/logout")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index/login";
    }
}
