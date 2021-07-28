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

}
