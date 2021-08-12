package com.ssafy.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.service.ArticleService;
import com.ssafy.common.service.CommentService;
import com.ssafy.common.service.NotificationService;

@RestController
@RequestMapping("/api/like")
public class LikeController {
	@Autowired
	ArticleService as;
	@Autowired
	CommentService cs;
	@Autowired
	NotificationService notificationService;

	@PostMapping("/{articleClass}/article/{pk}")
	public Map<String, Object> ArticleLike(
			@PathVariable(name = "articleClass") String articleClass,
			@PathVariable(name = "pk") long articlePk,
			@RequestBody Map<String, Object> req) {

		Map<String, Object> ret = as.likeArticle(articleClass, articlePk, req);

		//좋아요 취소할땐 알림 안감
		if (!articleClass.equals("discuss")&&(boolean)ret.get("myLike")) {
			notificationService.LikeArticle(articleClass, articlePk, (long) ret.get("member"));
			ret.remove("member");
		}

		return ret;
	}

	@PostMapping("/{articleClass}/comment/{pk}")
	public Map<String, Object> commentLike(
			@PathVariable(name = "articleClass") String articleClass,
			@PathVariable(name = "pk") long commentPk,
			@RequestBody Map<String, Object> req) {

		Map<String, Object> ret = cs.likeComment(articleClass, commentPk, req);

		//좋아요 취소할땐 알림 안감
		if((boolean)ret.get("myLike")) {
			notificationService.LikeComment(articleClass, (long)ret.get("articleNo"), (long) ret.get("member"));
			ret.remove("member");
			ret.remove("articleNo");
		}
		return ret;
	}

	// 대댓글 기능은 시간부족으로 빼기로함
	// @PostMapping("/{articleClass}/recomment/{pk}")
	// public Map<String,Object>reCommentLike(
	// @PathVariable(name="articleClass")String articleClass,
	// @PathVariable(name="pk") long articlePk,
	// @RequestBody Map<String,Object> req
	// ){
	// return as.likeArticle(articleClass, articlePk,req);
	// }
}
