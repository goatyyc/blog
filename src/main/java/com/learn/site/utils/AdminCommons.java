package com.learn.site.utils;

import com.learn.site.pojo.Meta;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
public class AdminCommons {
    /**
     * 判断category和cat的交集
     *
     * @param cats
     * @return
     */
    public static boolean exist_cat(Meta category, String cats) {
        String[] arr = StringUtils.split(cats, ",");
        if (null != arr && arr.length > 0) {
            for (String c : arr) {
                if (c.trim().equals(category.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    public static String rand_color(){
        int r = Tools.rand(0, COLORS.length-1);
        return COLORS[r];
    }
}
