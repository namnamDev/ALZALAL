package com.ssafy.common.repository;

import org.springframework.stereotype.Repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.article.QArticle_Like;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Article_LikeRepositoryImpl implements Article_LikeRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Article_Like ifMemberExist(Member member) {
		return queryFactory.selectFrom(QArticle_Like.article_Like).
				where(QArticle_Like.article_Like.member.eq(member)).fetchOne();
	}
	@Override
	public long deleteLike(Member member,Article article) {
		return queryFactory.delete(QArticle_Like.article_Like).
				where(QArticle_Like.article_Like.member.eq(member)).
				where(QArticle_Like.article_Like.articleNo.eq(article)).
				execute();
	}
	
}
