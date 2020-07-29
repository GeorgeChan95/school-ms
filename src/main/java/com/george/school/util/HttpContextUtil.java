package com.george.school.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 *     http工具类
 *     获取http请求上下文信息
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/28 19:37
 * @since JDK 1.8
 */
public class HttpContextUtil {
    private HttpContextUtil(){

    }
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
