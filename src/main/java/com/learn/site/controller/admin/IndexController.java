package com.learn.site.controller.admin;

import com.learn.site.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

public class IndexController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("")
    public String index(HttpServletRequest request){
        LOGGER.info("Enter admin index method");

        return null;
    }

}
