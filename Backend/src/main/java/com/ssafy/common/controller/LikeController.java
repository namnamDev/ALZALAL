package com.ssafy.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.service.ArticleService;

@RestController
@RequestMapping("/like")
public class LikeController {
	  @Autowired
	  ArticleService as;
	  
	  @PostMapping("/{articleClass}/article/{pk}")
	  public Map<String,Object>ArticleLike(
			  @PathVariable(name="articleClass")String articleClass,
			  @PathVariable(name="pk") long articlePk,
			  @RequestBody Map<String,Object> req
			  ){
		  return as.likeArticle(articleClass, articlePk,req);
	  }
	  
	  //예정
//	  @PostMapping("/{articleClass}/comment/{pk}")
//	  public Map<String,Object>commentLike(
//			  @PathVariable(name="articleClass")String articleClass,
//			  @PathVariable(name="pk") long articlePk,
//			  @RequestBody Map<String,Object> req
//			  ){
//		  return as.likeArticle(articleClass, articlePk,req);
//	  }
//	  
//	  @PostMapping("/{articleClass}/recomment/{pk}")
//	  public Map<String,Object>reCommentLike(
//			  @PathVariable(name="articleClass")String articleClass,
//			  @PathVariable(name="pk") long articlePk,
//			  @RequestBody Map<String,Object> req
//			  ){
//		  return as.likeArticle(articleClass, articlePk,req);
//	  }
}
