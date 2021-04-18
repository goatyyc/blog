package com.learn.site.controller.admin;

import com.learn.site.controller.BaseController;
import com.learn.site.pojo.Stars;
import com.learn.site.service.StarsService;
import com.learn.site.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/stars")
@Controller
public class StarsController extends BaseController {

    @Autowired
    private StarsService starsService;

    @RequestMapping("")
    @ResponseBody
    public APIResponse star(Integer cid, String ip){

        int result = starsService.addStarsById(cid, ip);
        if(result==1){
            return APIResponse.success();
        }else {
            return APIResponse.fail("已经点赞过~");
        }

    }

    @RequestMapping("/showLikes")
    @ResponseBody
    public APIResponse showLikes(Integer cid){
        int num = starsService.queryLikesNumById(cid);
        return APIResponse.success(num);
    }

}
