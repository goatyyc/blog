package com.learn.site.dao;

import com.learn.site.pojo.RelationShip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RelationShipMapper {

    Long getCountById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    int addRelationShip(RelationShip relationShip);

    int deleteRelationShipById(Integer cid);
}
