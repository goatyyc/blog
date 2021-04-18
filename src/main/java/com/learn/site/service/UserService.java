package com.learn.site.service;

import com.learn.site.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User login(String username, String pwd);
}
