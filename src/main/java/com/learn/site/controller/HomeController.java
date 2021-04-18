package com.learn.site.controller;

import com.github.pagehelper.PageInfo;
import com.learn.site.constant.Types;
import com.learn.site.dto.cond.ContentCond;
import com.learn.site.pojo.Content;
import com.learn.site.service.ContentService;
import com.learn.site.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/blog",""})
public class HomeController extends BaseController{
    @Autowired
    private ContentService contentService;

    @GetMapping("/about")
    public String article(HttpServletRequest request){
        return "site/about";
    }

    @GetMapping(value = {"","/index"})
    public String blogIndex(HttpServletRequest request){
        return this.index(request, 1, 5);
    }

    @GetMapping("/index/{page}")
    public String index(HttpServletRequest request, @PathVariable Integer page, Integer limit){
        // 初始化page 和 Limit
        page = page ==null ? 1 : page;
        limit = limit==null ? 5 : limit;
        // 分页查询文章
        PageInfo<Content> articles = contentService.getArticleByCond(new ContentCond(), page, limit);
        request.setAttribute("articles", articles);

        return "site/index";
    }


    @GetMapping("/contact")
    public String contact(HttpServletRequest request){
        return "site/contact";
    }


    @GetMapping("/article/{cid}")
    public String article(HttpServletRequest request, @PathVariable Integer cid){
        Content article = contentService.getArticleById(cid);
        request.setAttribute("article",article);
        String content = article.getContent();
        //content markdown渲染为html
        String article_content = Commons.article(content);
        request.setAttribute("content",article_content);


        return "site/article-details";
    }


}
