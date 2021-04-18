package com.learn.site.service.impl;

import com.learn.site.constant.ErrorConstant;
import com.learn.site.constant.WebConst;
import com.learn.site.dao.MetaMapper;
import com.learn.site.dao.RelationShipMapper;
import com.learn.site.dto.MetaDto;
import com.learn.site.dto.cond.MetaCond;
import com.learn.site.exception.BusinessException;
import com.learn.site.pojo.Meta;
import com.learn.site.pojo.RelationShip;
import com.learn.site.service.ContentService;
import com.learn.site.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
@Service
public class MetaServiceImpl implements MetaService {
    @Autowired
    private ContentService contentService;

    @Autowired
    private MetaMapper metaMapper;

    @Autowired
    private RelationShipMapper relationShipMapper;

    @Override
    @Cacheable(value = "metaCaches", key = "'metas_'+#p0")
    public List<Meta> getMetas(MetaCond metaCond) {
        return metaMapper.getMetaByCond(metaCond);
    }

    @Override
    @CacheEvict(value = {"metaCaches","metaCache"}, allEntries = true, beforeInvocation = true)
    public void addMetas(Integer cid, String names, String type) {
        if(null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        if(StringUtils.hasLength(names) && StringUtils.hasLength(type)){
            String[] nameArr = org.thymeleaf.util.StringUtils.split(names, ",");
            for (String name : nameArr) {
                this.saveOrUpdate(cid, name, type);
            }
        }
    }

    @Override
    @CacheEvict(value = {"metaCaches","metaCache"}, allEntries = true, beforeInvocation = true)
    public void saveOrUpdate(Integer cid, String name, String type) {
        MetaCond metaCond = new MetaCond();
        metaCond.setName(name);
        metaCond.setType(type);
        List<Meta> metas = this.getMetas(metaCond);

        int mid;
        if(metas.size() == 1){
            Meta meta = metas.get(0);
            mid = meta.getMid();
        }else if(metas.size()>1){
            // 结果集不止一个
            throw BusinessException.withErrorCode(ErrorConstant.Meta.NOT_ONE_RESULT);
        }else {
            Meta meta = new Meta();
            meta.setName(name);
            meta.setType(type);
            // 新增meta记录
            this.addMeta(meta);
            mid = meta.getMid();
        }

        // 添加文章分类的relationship
        if(mid != 0){
            Long count = relationShipMapper.getCountById(cid, mid);
            if(count == 0){
                RelationShip relationShip = new RelationShip();
                relationShip.setCid(cid);
                relationShip.setMid(mid);
                relationShipMapper.addRelationShip(relationShip);
            }
        }

    }

    @Override
    @CacheEvict(value = {"metaCaches","metaCache"}, allEntries = true, beforeInvocation = true)
    public void addMeta(Meta meta) {
        if(null == meta)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaMapper.addMeta(meta);
    }

    @Override
    @Cacheable(value = "metaCaches", key = "'metaList_'+#p0")
    public List<MetaDto> getMetaList(String type, String orderby, int limit) {
        if(StringUtils.hasLength(type)){
            orderby = "count desc, a.mid desc";

            if(limit<1 || limit> WebConst.MAX_POSTS){
                limit=10;
            }

            HashMap<String , Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaMapper.selectFromSql(paraMap);
        }
        return null;
    }

    @Override
    @CacheEvict(value = {"metaCaches","metaCache"}, allEntries = true, beforeInvocation = true)
    public void saveMeta(String type, String name, Integer mid) {
        if(StringUtils.hasLength(type) && StringUtils.hasLength(name)){
            MetaCond metaCond = new MetaCond();
            metaCond.setName(name);
            List<Meta> metas = metaMapper.getMetaByCond(metaCond);

            if(null == metas || metas.size() == 0){
                Meta metaPojo = new Meta();
                metaPojo.setName(name);
                if(null != mid){
                    // mid不为空则为更新
                    Meta meta = metaMapper.getMetaById(mid);
                    if(null != meta) metaPojo.setMid(mid);

                    //更新分类
                    metaMapper.updateMeta(metaPojo);

                    //更新原有文章分类
                    if(meta != null){
                        contentService.updateCategory(meta.getName(), name);
                    }
                }else {
                    metaPojo.setType(type);
                    metaMapper.addMeta(metaPojo);
                }

            }else {
                throw BusinessException.withErrorCode(ErrorConstant.Meta.META_IS_EXIST);
            }
        }
    }

    @Override
    public void deleteMetaById(Integer mid) {
        if(null == mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        Meta meta = metaMapper.getMetaById(mid);
        if(meta != null){
            String type = meta.getType();
            String name = meta.getName();
            metaMapper.deleteMetaById(mid);
        }
    }

    @Override
    @Cacheable(value = "metaCache", key = "'metaById'+#p0")
    public Meta getMetaById(Integer mid){
        if(null==mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return metaMapper.getMetaById(mid);
    }
}
