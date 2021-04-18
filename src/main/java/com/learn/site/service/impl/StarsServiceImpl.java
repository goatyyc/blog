package com.learn.site.service.impl;

import com.learn.site.dao.StarsMapper;
import com.learn.site.exception.BusinessException;
import com.learn.site.pojo.Content;
import com.learn.site.pojo.Stars;
import com.learn.site.service.ContentService;
import com.learn.site.service.StarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarsServiceImpl implements StarsService {

    @Autowired
    private StarsMapper starsMapper;

    @Autowired
    private ContentService contentService;

    @Override
    public int addStarsById(Integer cid, String ip) {
        //判断是否已经点赞，通过cid,ip 查询
        Stars stars = new Stars();
        stars.setCid(cid);
        stars.setIp(ip);
        //已点过赞则提醒
        if (isExist(stars))
            return 0;
        starsMapper.addStarsById(stars);
        contentService.updateArticleLikesNum(cid);
        return 1;
    }

    /**
     * 判断该点赞记录是否存在
     * @param stars
     * @return
     */
    @Override
    public Boolean isExist(Stars stars) {
        return starsMapper.isExist(stars);
    }

    @Override
    public int queryLikesNumById(Integer cid) {
        Content content = starsMapper.queryLikes(cid);
        return content.getLikes();
    }


}
