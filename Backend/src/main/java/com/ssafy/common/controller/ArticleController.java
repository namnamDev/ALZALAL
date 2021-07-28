package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.common.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
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
//		  ,long memberPk//유저jwt에서 pk추출
		  ){
	  //테스트용 memberPk=1
	  long memberPk = 1;
	  return as.deleteArticle(articleClass,articlePk,memberPk);
  }
  @PutMapping("/{articleClass}/{pk}")
  public Map<String,Object>updateArticle(
		  @PathVariable(name="articleClass")String articleClass,
		  @PathVariable(name="pk") long articlePk
		  ,@RequestBody Map<String,Object> req
		  ){
	  //변경된 회원을 임시로 담아줌 
	  long memberPk = 1;
	  return as.updateArticle(articleClass,articlePk,memberPk,req);
  }
}