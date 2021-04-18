package com.learn.site.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learn.site.constant.ErrorConstant;
import com.learn.site.constant.Types;
import com.learn.site.dao.ContentMapper;
import com.learn.site.dao.MetaMapper;
import com.learn.site.dao.RelationShipMapper;
import com.learn.site.dto.cond.ContentCond;
import com.learn.site.exception.BusinessException;
import com.learn.site.pojo.Content;
import com.learn.site.service.CommentService;
import com.learn.site.service.ContentService;
import com.learn.site.service.MetaService;
import com.learn.site.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {


    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private MetaService metaService;

    @Autowired
    private RelationShipMapper relationShipMapper;

    @Autowired
    private CommentService commentService;

    @Override
    @CacheEvict(value = {"articleCache","articleCaches"}, allEntries = true, beforeInvocation = true)
    public void addArticle(Content content) {
        // 校验参数是否为空
        if(null == content)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if(!StringUtils.hasLength(content.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if(!StringUtils.hasLength(content.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);

        // 分类
        String categories = content.getCategories();

        contentMapper.addArticle(content);
        int cid = content.getCid();
//        // 测试事务，抛出异常这里
//        if(cid != 0){
//            throw BusinessException.withErrorCode(ErrorConstant.Common.INVALID_PARAM);
//        }
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());

    }

    @Override
    @Cacheable(value = "articleCaches", key = "'articlesByCond_' + #p1+'type_'+#p0.type")
    public PageInfo<Content> getArticleByCond(ContentCond contentCond, int pageNum, int pageSize) {
        if(null == contentCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum,pageSize);
        List<Content> contents = contentMapper.getArticlesByCond(contentCond);
        PageInfo<Content> pageInfo = new PageInfo<>(contents);

        return pageInfo;
    }

    @Override
    @Cacheable(value = "articleCache", key = "'articlesById_' + #p0")
    public Content getArticleById(Integer cid) {
        if(null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentMapper.getArticleById(cid);
    }

    @Transactional
    @Override
    @CacheEvict(value = {"articleCache","articleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateCategory(String ordinal, String newCategory) {
        ContentCond contentCond = new ContentCond();
        contentCond.setCategory(ordinal);
        List<Content> articles = contentMapper.getArticlesByCond(contentCond);
        articles.forEach(article ->{
            article.setCategories(article.getCategories().replace(ordinal,newCategory));
            // 入库
            contentMapper.updateArticleById(article);
        });
    }

    @Transactional
    @Override
    @CacheEvict(value = {"articleCache","articleCaches"},allEntries = true, beforeInvocation = true)
    public void deleteArticleById(Integer cid) {
        if(null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        contentMapper.deleteArticleById(cid);

        // 删除标签和分类关联
        relationShipMapper.deleteRelationShipById(cid);
        // 删除相关评论
        commentService.deleteArticleComment(cid);
    }

    @Transactional
    @Override
    @CacheEvict(value = {"articleCache","articleCaches"}, allEntries=true, beforeInvocation = true)
    public void updateArticleById(Content contentPojo) {
        // 分类
        String categories = contentPojo.getCategories();

        contentMapper.updateArticleById(contentPojo);
        int cid = contentPojo.getCid();
        relationShipMapper.deleteRelationShipById(cid);

        metaService.addMetas(cid,categories, Types.CATEGORY.getType());
    }

    /**
     * 数据库likes字段值+1
     */
    @Override
    public void updateArticleLikesNum(Integer cid) {
        contentMapper.updateArticleLikes(cid);
    }

}
