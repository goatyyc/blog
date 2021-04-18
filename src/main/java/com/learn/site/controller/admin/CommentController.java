package com.learn.site.controller.admin;

import com.learn.site.controller.BaseController;
import com.learn.site.exception.BusinessException;
import com.learn.site.pojo.Comment;
import com.learn.site.service.CommentService;
import com.learn.site.utils.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/comment")
@Slf4j
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/commit")
    @ResponseBody
    public APIResponse comment(HttpServletRequest request, Integer cid, String username, String comment){
        log.info(String.valueOf(cid));
        log.info(username);
        log.info(comment);
        //判断是否为空
        if(!(StringUtils.hasLength(cid.toString()) && StringUtils.hasLength(username) && StringUtils.hasLength(comment))){
            String msg = "参数不能为空";
            return APIResponse.fail(msg);
        }

        Comment commentPojo = new Comment();
        commentPojo.setCid(cid);
        commentPojo.setUsername(username);
        commentPojo.setContents(comment);
        try{
            commentService.comment(commentPojo);
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.fail("留言失败");
        }

        return APIResponse.success();
    }

    @PostMapping("/getComment")
    @ResponseBody
    public APIResponse getComment(Integer cid){
        List<Comment> comments = commentService.getComment(cid);
        return APIResponse.success(comments);
    }

    @PostMapping("/delComment")
    @ResponseBody
    public APIResponse deleteComment(Integer ccid){
        try {
            commentService.deleteComment(ccid);
        } catch (Exception e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode("fail");
        }
        return APIResponse.success();
    }
}
