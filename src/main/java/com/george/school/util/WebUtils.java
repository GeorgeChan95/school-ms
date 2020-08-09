package com.george.school.util;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jlw
 * @date 2020/4/8
 * @description
 */
public class WebUtils {

    /**
     * 判断是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    /**
     * 判断是否ajax请求
     * spring ajax 返回含有 ResponseBody 或者 RestController注解
     * @param handlerMethod HandlerMethod
     * @return 是否ajax请求
     */
    public static boolean isAjax1(HandlerMethod handlerMethod) {
        ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (null != responseBody) {
            return true;
        }
        // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
        Class<?> beanType = handlerMethod.getBeanType();
        responseBody = AnnotationUtils.getAnnotation(beanType, ResponseBody.class);
        if (null != responseBody) {
            return true;
        }
        return false;
    }


}


