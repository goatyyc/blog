package com.learn.site.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learn.site.dto.cond.ContentCond;
import com.learn.site.pojo.Content;
import org.springframework.stereotype.Service;

public interface ContentService {

    void addArticle(Content content);

    PageInfo<Content> getArticleByCond(ContentCond contentCond, int pageNum, int pageSize);

    Content getArticleById(Integer cid);

    /**
     * 更新分类
     * @param ordinal
     * @param newCategory
     */
    void updateCategory(String ordinal, String newCategory);

    /**
     * 根据文章编号删除文章
     * @param cid
     */
    void deleteArticleById(Integer cid);

    void updateArticleById(Content contentPojo);

    void updateArticleLikesNum(Integer cid);
}
