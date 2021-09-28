package com.spring.springfamily.es.controller;

import com.spring.springfamily.es.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JdEsController
 * @Description
 * @Author guopy
 * @Date 2021/9/28 11:08
 */
@Controller
public class JdEsController {

    @Autowired
    private ContentService contentService;


    @ResponseBody
    @GetMapping("/parse/{keywords}")
    public Boolean parses(@PathVariable("keywords")String keywords) throws Exception {
        return contentService.parseContents(keywords);
    }

    @ResponseBody
    @GetMapping("/search/{keywords}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keywords")String keywords, @PathVariable("pageNo")Integer pageNo,
                                            @PathVariable("pageSize")Integer pageSize) throws IOException {
        return contentService.highlightBuilder(keywords, pageNo, pageSize);
    }

}
