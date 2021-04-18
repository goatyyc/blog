package com.learn.site.dao;

import com.learn.site.pojo.Content;
import com.learn.site.pojo.Stars;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StarsMapper {

    int addStarsById(Stars stars);

    Boolean isExist(Stars stars);

    Content queryLikes(Integer cid);
}
