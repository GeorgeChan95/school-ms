package com.george.school.controller;

import com.george.school.util.DrawImageUtil;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 * 生成/校验图片验证码前端控制器
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/24 20:07
 * @since JDK 1.8
 */
@Slf4j
@Api(tags="图片验证码")
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    /**
     * 生成图片验证码
     *
     * @param flag     验证码创建类型标记
     * @param request  请求
     * @param response 响应
     */
    @ApiOperation("生成图片验证码")
    @GetMapping(value = "/drawImage")
    public void DrawImage(@ApiParam("验证码创建标记") @RequestParam("createTypeFlag") String flag, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.在内存中创建一张图片
        BufferedImage bi = new BufferedImage(DrawImageUtil.WIDTH, DrawImageUtil.HEIGHT, BufferedImage.TYPE_INT_RGB);
        //2.得到图片
        Graphics g = bi.getGraphics();
        //3.设置图片的背影色
        DrawImageUtil.setBackGround(g);
        //4.设置图片的边框
        DrawImageUtil.setBorder(g);
        //5.在图片上画干扰线
        DrawImageUtil.drawRandomLine(g);
        String random = DrawImageUtil.drawRandomNum((Graphics2D) g, flag);//根据客户端传递的createTypeFlag标识生成验证码图片
        //7.将随机数存在session中
        request.getSession().setAttribute("captchaCode", random);
        //8.设置响应头通知浏览器以图片的形式打开
        response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
        //9.设置响应头控制浏览器不要缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        //10.将图片写给浏览器
        ImageIO.write(bi, "jpg", response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param request     请求
     * @param captchaCode 用户输入的验证码
     * @return
     */
    @ApiOperation("校验图片验证码")
    @PostMapping(value = "/validateCaptcha")
    public Result validateCaptcha(HttpServletRequest request, @ApiParam("验证码") @RequestParam(value = "captchaCode", required = false) String captchaCode) {
        boolean br = false;
        //从服务器端的session中取出验证码
        String serverCaptchaCode = (String) request.getSession().getAttribute("captchaCode");
        //将客户端验证码和服务器端验证比较，如果相等，则表示验证通过
        if (StringUtils.equals(captchaCode, serverCaptchaCode)) {
            return new Result(true, StatusCode.OK, "验证码正确");
        }
        return new Result(false, StatusCode.ERROR, "验证码错误");
    }
}
