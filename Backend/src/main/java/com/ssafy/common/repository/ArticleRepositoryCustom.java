package com.ssafy.common.repository;

import java.util.List;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;

public interface ArticleRepositoryCustom {

	List<Article> findAll();

	List<Article_Comment> articleComments(Article article);

	Article insertArticle(Member member, Article myinsert);

	long articleDelete(Long articlePk);

	long updateArticle(long articlePk, String content);

	//작성자를 통한 게시글 갯수 조회
	long countByMember(Long memberNo);

	long likeArticle(Article article);

	Article sltOne(Long pk);
}
