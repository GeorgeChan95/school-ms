package com.george.school.util;

import cn.hutool.core.util.ObjectUtil;
import com.george.school.config.ConfigProperties;
import com.george.school.constants.SystemConst;
import com.george.school.enums.ImageTypeEnum;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>
 * 验证码的生成、校验等操作
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/29 19:02
 * @since JDK 1.8
 */
@Service
@RequiredArgsConstructor
public class ValidateCodeService {
    private final RedisService redisService;
    private final ConfigProperties properties;

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String key = session.getId();
        // 设置请求头
        setHeader(response, properties.getCodeType());
        // 创建验证码
        Captcha captcha = createCaptch(properties);
        redisService.set(SystemConst.CODE_PREFIX + key, StringUtils.lowerCase(captcha.text()), properties.getCodeTime());
        captcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码是否正确
     *
     * @param key
     * @param value
     */
    public void check(String key, String value) {
        Object code = redisService.get(SystemConst.CODE_PREFIX + key);
        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException("请输入验证码");
        }
        if (ObjectUtil.isNull(code)) {
            throw new RuntimeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(code))) {
            throw new RuntimeException("验证码不正确");
        }
    }

    /**
     * 创建验证码
     *
     * @param properties 验证码的配置属性
     * @return
     */
    private Captcha createCaptch(ConfigProperties properties) {
        Captcha captcha;
        if (StringUtils.equalsIgnoreCase(properties.getCodeType(), ImageTypeEnum.GIF.getCode())) {
            captcha = new GifCaptcha(properties.getCodeWidth(), properties.getCodeHeight(), properties.getCodeLength());
        } else {
            captcha = new SpecCaptcha(properties.getCodeWidth(), properties.getCodeHeight(), properties.getCodeLength());
        }
        captcha.setCharType(properties.getCodeCharType());
        return captcha;
    }

    /**
     * 根据验证码的类型设置请求头信息
     *
     * @param response
     * @param codeType
     */
    private void setHeader(HttpServletResponse response, String codeType) {
        if (StringUtils.equalsIgnoreCase(codeType, ImageTypeEnum.GIF.getCode())) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }
}
