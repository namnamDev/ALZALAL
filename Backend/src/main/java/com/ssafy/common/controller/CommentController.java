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
import com.ssafy.common.service.NotificationService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
	CommentService cs;
	@Autowired
	NotificationService notificationService;
	@GetMapping("/{articleClass}/{pk}")
	public Map<String, Object> sltMultiArticleComment(
			@PathVariable(name = "articleClass") String articleClass,
			@PathVariable(name = "pk") long articlePk,
			@RequestParam(defaultValue = "0") int page) {
		return cs.sltMultCommentByArticle(articleClass, articlePk, page);
	}
	@PostMapping("/{articleClass}/{pk}")
	public Map<String, Object> insertArticleComment(
			@PathVariable(name = "articleClass") String articleClass,
			@PathVariable(name = "pk") long articlePk,
			@RequestBody Map<String, Object> req) {
		Map<String, Object> ret =
				cs.insertArticleComment(articleClass, articlePk, req);
		
		//토론게시판 빼고 알림 발생
		if(!articleClass.equals("discussion")) {
			notificationService.articleComment(articleClass,articlePk, (long)ret.get("member"));
			ret.remove("member");
		}
		return ret;
	}
	// 댓글삭제
	@DeleteMapping("/{articleClass}/{pk}")
	public Map<String, Object> deletedArticCom(
			@PathVariable(name = "articleClass") String articleClass,
			@PathVariable(name = "pk") long commentPK) {
		return cs.deleteArticleComment(articleClass, commentPK);

	}
	@PutMapping("/{articleClass}/{pk}")
	public Map<String, Object> updateArticCom(
			@PathVariable(name = "articleClass") String articleClass,
			@PathVariable(name = "pk") long commentPK,
			@RequestBody Map<String, Object> req) {
		return cs.updateArtiComment(articleClass, commentPK, req);

	}

}
