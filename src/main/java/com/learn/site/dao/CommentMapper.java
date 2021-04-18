package com.learn.site.dao;

import com.learn.site.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int addComment(Comment comment);

    int delComment(Integer ccid);

    int delAllComment(Integer cid);

    List<Comment> getComment(Integer cid);

    List<Comment> getAllComment();
}
