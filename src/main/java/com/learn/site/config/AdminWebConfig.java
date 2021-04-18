package com.learn.site.config;

import com.learn.site.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/admin/login","/admin/css/**","/admin/img/**")
//                .excludePathPatterns("/admin/editormd/**","/admin/images/**","/admin/plugins/**")
//                .excludePathPatterns("/admin/js/**","/admin/images/**")
//                .excludePathPatterns("/site/**", "/comment/**")
//                .excludePathPatterns("/blog/**", "/blog/","/");
    }
}
