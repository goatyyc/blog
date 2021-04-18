package com.learn.site.dao;

import com.learn.site.dto.MetaDto;
import com.learn.site.dto.cond.MetaCond;
import com.learn.site.pojo.Meta;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
@Mapper
public interface MetaMapper {

    Meta getMetaById(@Param("mid") Integer id);

    List<Meta> getMetaByCond(MetaCond metaCond);


    /**
     * 添加项目
     * @param meta
     * @return
     */
    int addMeta(Meta meta);

    List<MetaDto> selectFromSql(HashMap<String, Object> paraMap);

    /**
     * 更新项目
     * @param metaPojo
     * @return
     */
    int updateMeta(Meta metaPojo);

    /**
     * 删除项目
     * @param mid
     * @return
     */
    int deleteMetaById(Integer mid);
}
