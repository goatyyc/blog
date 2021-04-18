package com.learn.site.service;


import com.github.pagehelper.PageInfo;
import com.learn.site.dao.CommentMapper;
import com.learn.site.pojo.Comment;

import java.util.List;

public interface CommentService {
    void comment(Comment comment);

    List<Comment> getComment(Integer cid);

    void deleteComment(Integer ccid);

    void deleteArticleComment(Integer cid);

    PageInfo<Comment> getAllComment(int pageNum, int pageSize);
}
