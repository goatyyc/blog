package com.learn.site.dto;

import com.learn.site.pojo.Meta;

/**
 * 标签、分类列表
 */
public class MetaDto extends Meta {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
