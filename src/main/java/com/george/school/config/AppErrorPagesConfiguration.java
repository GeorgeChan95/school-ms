package com.george.school.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * 配置http响应码为404和500时，跳转的url
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/1 10:59
 * @since JDK 1.8
 */
@Configuration
public class AppErrorPagesConfiguration {
    @Bean
    public MyErrorPageRegistrar errorPageRegistrar() {
        return new MyErrorPageRegistrar();
    }

    private class MyErrorPageRegistrar implements ErrorPageRegistrar {
        @Override
        public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
            ErrorPage page401 = new ErrorPage(HttpStatus.UNAUTHORIZED, "/index/unauthorized");
            ErrorPage page403 = new ErrorPage(HttpStatus.FORBIDDEN, "/index/403");
            ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/index/404");
            ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/index/500");

            errorPageRegistry.addErrorPages(page401, page403, page404, page500);
        }
    }
}
