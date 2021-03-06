package com.ssafy.common.controller;

import java.util.Map;

import com.ssafy.common.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
  @Autowired
  ArticleService as;
  @GetMapping("/{articleClass}/{pk}")
  public Map<String,Object>sltOneArticle(
		  @PathVariable(name="articleClass")String articleClass,
		  @PathVariable(name="pk") long pk){
	  return as.sltOneArticle(articleClass, pk);
  }  
  @DeleteMapping("/{articleClass}/{pk}")
  public Map<String,Object>deleteArticle(
		  @PathVariable(name="articleClass")String articleClass,
		  @PathVariable(name="pk") long articlePk
		  ){
	  return as.deleteArticle(articleClass,articlePk);
  }
  @PutMapping("/{articleClass}/{pk}")
  public Map<String,Object>updateArticle(
		  @PathVariable(name="articleClass")String articleClass,
		  @PathVariable(name="pk") long articlePk,
		  @RequestBody Map<String,Object> req
		  ){
	  return as.updateArticle(articleClass,articlePk,req);
  }
  @PostMapping("/{articleClass}")
  public Map<String,Object>insertArticle(
		  @PathVariable(name="articleClass")String articleClass,
		  @RequestBody Map<String,Object> req
		  ){
	  return as.insertArticle(articleClass, req);
  }
}