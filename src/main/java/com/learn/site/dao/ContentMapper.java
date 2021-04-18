package com.learn.site.dao;

import com.learn.site.dto.cond.ContentCond;
import com.learn.site.pojo.Content;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentMapper {

    int addArticle(Content content);

    List<Content> getArticlesByCond(ContentCond contentCond);

    int updateArticleById(Content content);

    int deleteArticleById(Integer cid);

    Content getArticleById(Integer cid);

    void updateArticleLikes(Integer cid);
}
