package com.learn.site.service;

import com.learn.site.pojo.Stars;

public interface StarsService {

    int addStarsById(Integer cid, String ip);

    Boolean isExist(Stars stars);

    int queryLikesNumById(Integer cid);
}
