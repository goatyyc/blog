package com.learn.site.controller;

import com.learn.site.utils.AdminCommons;
import com.learn.site.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;

}
