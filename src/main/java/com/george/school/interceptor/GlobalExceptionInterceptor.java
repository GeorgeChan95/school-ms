package com.george.school.interceptor;

import cn.hutool.core.util.RandomUtil;
import com.george.school.util.IpUtil;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 全局异常捕获
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/9 17:45
 * @since JDK 1.8
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionInterceptor {
    /**
     * 捕获全局异常（1.内部程序throw RuntimeException时，务必自定义异常信息；2.或自定义异常类MyException，定义code与msg，内部throw MyException）
     * 打印相应的异常信息
     *
     * @param request 请求
     * @param e       异常
     * @return 返回json数据
     */
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(HttpServletRequest request, Exception e) {
        String msg = e.getMessage();
        String uuid = RandomUtil.randomString(20);
        log.error("=============================打印异常信息=============================");
        log.error("异常唯一值===> {}", uuid);
        log.error("异常信息===> {}", msg);
        log.error("请求主机===> {}", IpUtil.getIpAddr(request));
        log.error("****************************堆栈信息****************************");
        e.printStackTrace();
        log.error("****************************堆栈信息****************************");
        log.error("=============================打印异常信息=============================");
        if (WebUtils.isAjax(request)) {
            return new Result(false, StatusCode.ERROR, msg, uuid);
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", request.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            modelAndView.setViewName("error/500");
            return modelAndView;
        }
    }

    /**
     * 处理validation 框架异常
     * @throws
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        if (CollectionUtils.isEmpty(errors)) {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        return new Result(false, StatusCode.ERROR, errors.get(0).getDefaultMessage());
    }
}
