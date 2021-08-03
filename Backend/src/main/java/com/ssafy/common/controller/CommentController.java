package com.ssafy.common.controller;

import java.util.Map;

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

import com.ssafy.common.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	CommentService cs;
  @GetMapping("/{articleClass}/{pk}")
  public Map<String,Object>sltMultiArticleComment(
		  @PathVariable(name="articleClass")String articleClass,
		  @PathVariable(name="pk") long articlePk,
		  @RequestParam(defaultValue = "0") int page
		  ){
	  return cs.sltMultCommentByArticle(articleClass,articlePk,page);
  }
  @PostMapping("/{articleClass}/{pk}")
  public Map<String,Object>insertArticleComment(
	  @PathVariable(name="articleClass")String articleClass,
	  @PathVariable(name="pk") long articlePk,
	  @RequestBody Map<String,Object> req
	  ){
  return cs.insertArticleComment(articleClass,articlePk,req);
  }
  //댓글삭제
  @DeleteMapping("/{articleClass}/{pk}")
  public Map<String,Object>deletedArticCom(
		  @PathVariable(name="articleClass")String articleClass,
		  @PathVariable(name="pk") long commentPK){
			return cs.deleteArticleComment(articleClass,commentPK);
	  
  }
  @PutMapping("/{articleClass}/{pk}")
	public Map<String,Object>updateArticCom(
			  @PathVariable(name="articleClass")String articleClass,
			  @PathVariable(name="pk") long commentPK,
			  @RequestBody Map<String,Object> req
			  ){
				return cs.updateArtiComment(articleClass,commentPK,req);
		  
	}
  //댓글 업데이트
  //댓글 좋아요

}
