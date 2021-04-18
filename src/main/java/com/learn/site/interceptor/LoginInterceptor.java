package com.learn.site.interceptor;

import com.learn.site.utils.AdminCommons;
import com.learn.site.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;

    /**
     * 目标方法执行前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录逻辑
        HttpSession session = request.getSession();
        Object user = session.getAttribute("userId");
        if(user != null){
            return true;
        }
        response.sendRedirect("/admin/login");
        return false;
    }

}
