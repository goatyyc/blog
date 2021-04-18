package com.learn.site.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.util.StringUtils;

public class BlogUtils {
    public static String mdToHtml(String markdown) {
        if(!StringUtils.hasLength(markdown)){
            return "";
        }

        // 解析markdown
        MutableDataSet mutableDataSet = new MutableDataSet();
        Parser parser = Parser.builder(mutableDataSet).build();
        HtmlRenderer renderer = HtmlRenderer.builder(mutableDataSet).build();

        Document document = parser.parse(markdown);
        String html = renderer.render(document);

        return html;
    }
}
