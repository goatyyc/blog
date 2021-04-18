package com.learn.site.service;

import com.learn.site.dto.MetaDto;
import com.learn.site.dto.cond.MetaCond;
import com.learn.site.pojo.Meta;


import java.util.List;
public interface MetaService {

    List<Meta> getMetas(MetaCond metaCond);

    void addMetas(Integer cid, String names, String type);

    void saveOrUpdate(Integer cid, String names, String type);

    /**
     * 添加项目
     * @param meta
     */
    void addMeta(Meta meta);

    List<MetaDto> getMetaList(String type, String orderby, int limit);

    void saveMeta(String type, String name, Integer mid);

    void deleteMetaById(Integer mid);

    Meta getMetaById(Integer mid);
}
