package com.ssafy.common.controller;

import java.util.Map;

import com.ssafy.common.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articlelist")
public class ArticleListController {
  @Autowired
  ArticleService as;
  @GetMapping("/{articleClass}")
  public Map<String,Object>sltMultiArticle(@PathVariable(name="articleClass") String articleClass){
    return as.sltMultiArticle(articleClass);
  }

}
