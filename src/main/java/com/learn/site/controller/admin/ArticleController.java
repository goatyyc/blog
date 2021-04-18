package com.learn.site.controller.admin;

import com.github.pagehelper.PageInfo;
import com.learn.site.constant.Types;
import com.learn.site.controller.BaseController;
import com.learn.site.dto.cond.ContentCond;
import com.learn.site.dto.cond.MetaCond;
import com.learn.site.pojo.Content;
import com.learn.site.pojo.Meta;
import com.learn.site.service.ContentService;
import com.learn.site.service.MetaService;
import com.learn.site.utils.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/article")
public class ArticleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ContentService contentService;

    @Autowired
    private MetaService metaService;

    @GetMapping("")
    public String index(HttpServletRequest request, Integer page, Integer limit){
        page = page==null ? 0 : page;
        limit = limit==null ? 5 : limit;
        PageInfo<Content> articles = contentService.getArticleByCond(new ContentCond(), page, limit);
        request.setAttribute("articles",articles);
        return "admin/article_list";
    }

    @GetMapping("/publish")
    public String newArticle(HttpServletRequest request){
        MetaCond metaCond = new MetaCond();
        metaCond.setType(Types.CATEGORY.getType());
        List<Meta> metas = metaService.getMetas(metaCond);
        request.setAttribute("categories",metas);
        return "admin/article_edit";
    }

    @PostMapping("/publish")
    @ResponseBody
    public APIResponse publishArticle(
            HttpServletRequest request,String title,String titlePic,
            String slug,String content,String type,String status,
            String tags,String categories, Boolean allowComment){

        Content contentObj = new Content();
        contentObj.setTitle(title);
        contentObj.setTitlePic(titlePic);

        contentObj.setContent(content);
        contentObj.setType(type);
        contentObj.setStatus(status);
        // 只允许博客文章有分类
        contentObj.setCategories(type.equals(Types.ARTICLE.getType()) ? categories:null );
        contentObj.setAllowComment(allowComment ? 1:0);

        contentService.addArticle(contentObj);

        return APIResponse.success();
    }

    @GetMapping("/{cid}")
    public String editArticle(@PathVariable Integer cid, HttpServletRequest request){
        Content content = contentService.getArticleById(cid);
        request.setAttribute("contents",content);
        MetaCond metaCond = new MetaCond();
        metaCond.setType(Types.CATEGORY.getType());
        List<Meta> categories = metaService.getMetas(metaCond);
        request.setAttribute("categories",categories);
        request.setAttribute("active","article");

        return "admin/article_edit";
    }


    @PostMapping("/modify")
    @ResponseBody
    public APIResponse modifyArticle(HttpServletRequest request, Integer cid, String title, String titlePic,
                                     String slug, String content, String type, String status, String categories,
                                     Boolean allowComment
                                     ){
        Content contentPojo = new Content();
        contentPojo.setCid(cid);
        contentPojo.setTitle(title);
        contentPojo.setTitlePic(titlePic);
        contentPojo.setContent(content);
        contentPojo.setType(type);
        contentPojo.setStatus(status);
        contentPojo.setCategories(categories);
        contentPojo.setAllowComment(allowComment ? 1 : 0);

        contentService.updateArticleById(contentPojo);
        return APIResponse.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public APIResponse deleteArticle(Integer cid, HttpServletRequest request){
        contentService.deleteArticleById(cid);
        return APIResponse.success();
    }
}
