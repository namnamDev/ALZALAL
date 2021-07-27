package com.ssafy.common.controller;

import java.util.Map;

import com.ssafy.common.service.ArticleService;
import com.ssafy.common.service.ArticleServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articlelist")
public class ArticleController {
  @Autowired
  ArticleService as;
  @GetMapping("/{articleClass}")
  public Map<String,Object>getArticle(@PathVariable(name="articleClass") String articleClass){
    System.out.println("1*****getArticle****"+articleClass);
    // as = new ArticleServiceImpl();
    return as.sltMultiArticle(articleClass);
  }
  // 전체조회목록작성
  // @Autowired
  // private 
}
