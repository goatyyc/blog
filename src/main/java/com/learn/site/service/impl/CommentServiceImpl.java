package com.learn.site.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learn.site.dao.CommentMapper;
import com.learn.site.pojo.Comment;
import com.learn.site.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public void comment(Comment comment) {
        // 调用mapper持久化数据
        commentMapper.addComment(comment);
    }

    @Override
    public List<Comment> getComment(Integer cid) {
        List<Comment> comments = commentMapper.getComment(cid);
        return comments;
    }

    @Override
    public void deleteComment(Integer ccid) {
        commentMapper.delComment(ccid);
    }

    @Override
    public void deleteArticleComment(Integer cid) {
        commentMapper.delAllComment(cid);
    }

    @Override
    public PageInfo<Comment> getAllComment(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Comment> comments = commentMapper.getAllComment();
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        return pageInfo;
    }


}