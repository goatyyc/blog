package com.learn.site.controller.admin;

import com.github.pagehelper.PageInfo;
import com.learn.site.constant.WebConst;
import com.learn.site.controller.BaseController;
import com.learn.site.pojo.Comment;
import com.learn.site.pojo.User;
import com.learn.site.service.CommentService;
import com.learn.site.service.UserService;
import com.learn.site.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AuthController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"","/index"})
    public String index(HttpServletRequest request, Integer page){
        //查询评论
        page = page==null ? 1 : page;
        Integer size = 10;
        PageInfo<Comment> comments = commentService.getAllComment(page, size);

        request.setAttribute("comments", comments);
        return "admin/index";
    }

    @GetMapping("/header")
    public String header(){
        return "admin/header";
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public APIResponse login(HttpServletRequest request, String username, String pwd){
        User userInfo = userService.login(username,pwd);
        if(null == userInfo){
            return APIResponse.fail("no such user");
        }
        // 登录成功
        request.getSession().setAttribute("userId",userInfo);
        return APIResponse.success();
    }

    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
        cookie.setValue(null);
        cookie.setMaxAge(0);    // 立即销毁cookie
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
