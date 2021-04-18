package com.learn.site.pojo;

import lombok.Data;

@Data
public class Content {
    private Integer cid;

    private String title;

    private String titlePic;

    private Integer modified;

    private Integer created;

    private String content;

    private Integer likes;

    private Integer authorId;

    private String type;

    private String status;

    private String categories;

    private Integer commentsNum;

    private Integer allowComment;

    private Integer allowPing;
}
