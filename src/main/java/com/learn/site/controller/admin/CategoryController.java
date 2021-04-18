package com.learn.site.controller.admin;

import com.learn.site.constant.Types;
import com.learn.site.constant.WebConst;
import com.learn.site.controller.BaseController;
import com.learn.site.dto.MetaDto;
import com.learn.site.exception.BusinessException;
import com.learn.site.service.MetaService;
import com.learn.site.utils.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("admin/category")
public class CategoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private MetaService metaService;

    @GetMapping("")
    public String index(HttpServletRequest request){
        List<MetaDto> categories = metaService.getMetaList(Types.CATEGORY.getType(), null, WebConst.MAX_POSTS);
        List<MetaDto> tags = metaService.getMetaList(Types.TAG.getType(), null, WebConst.MAX_POSTS);
        request.setAttribute("categories",categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }

    @PostMapping("save")
    @ResponseBody
    public APIResponse addCategory(String cname,Integer mid){
        try {
            metaService.saveMeta(Types.CATEGORY.getType(), cname, mid);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "分类保存失败";
            LOGGER.error(msg,e);
            return APIResponse.fail(msg);
        }

        return APIResponse.success();
    }

    @PostMapping("delete")
    @ResponseBody
    public APIResponse delete(Integer mid){
        try {
            metaService.deleteMetaById(mid);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }


}
