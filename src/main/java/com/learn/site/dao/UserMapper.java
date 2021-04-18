package com.learn.site.dao;

import com.learn.site.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User getUserInfoByCond(@Param("username") String username, @Param("password") String pwd);
}
