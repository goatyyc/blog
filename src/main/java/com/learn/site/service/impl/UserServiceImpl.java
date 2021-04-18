package com.learn.site.service.impl;

import com.learn.site.dao.UserMapper;
import com.learn.site.pojo.User;
import com.learn.site.service.UserService;
import com.learn.site.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String pwd) {
        if(!(StringUtils.hasLength(username) && StringUtils.hasLength(pwd))){
            return null;
        }
        String password = MD5Utils.MD5encode(username + pwd);
        User user = userMapper.getUserInfoByCond(username, password);
        if(null == user){
            return null;
        }
        return user;
    }
}
