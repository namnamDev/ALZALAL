package com.ssafy.common.controller;

import java.util.Map;

import com.ssafy.common.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articlelist")
public class ArticleListController {
  @Autowired
  ArticleService as;
  @GetMapping("/{articleClass}")
  public Map<String,Object>sltMultiArticle(@PathVariable(name="articleClass") String articleClass,
		  								   @RequestParam(defaultValue = "0") int page){
    return as.sltMultiArticle(articleClass,page);
  }
  
  @GetMapping("/discussion/{hostPK}")
  public Map<String,Object>sltMultiDiscussByHost(@PathVariable(name="hostPK") Long HostPK,
		  										 @RequestParam(defaultValue = "0") int page
		  ){
	  return as.sltMultiDiscussByHost(HostPK, page);
  }
}
