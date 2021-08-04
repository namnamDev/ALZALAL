package com.ssafy.common.repository.article;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import com.ssafy.common.domain.article.Article_Comment_Like;

import com.ssafy.common.domain.article.QArticle_Comment_Like;

@Repository
@RequiredArgsConstructor
public class Article_Comment_LikeRepositoryImpl implements Article_Comment_LikeRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Article_Comment_Like ifMemberExist(Long articlePK,Long memberNo) {
		return queryFactory.selectFrom(QArticle_Comment_Like.article_Comment_Like).
				where(QArticle_Comment_Like.article_Comment_Like.member.no.eq(memberNo).
						and(QArticle_Comment_Like.article_Comment_Like.articleComment.articleCommentNo.eq(articlePK))).fetchOne();
	}
	@Override
	public long deleteLike(Long memberNo,Long articlePK) {
		return queryFactory.delete(QArticle_Comment_Like.article_Comment_Like).
				where(QArticle_Comment_Like.article_Comment_Like.member.no.eq(memberNo)).
				where(QArticle_Comment_Like.article_Comment_Like.articleComment.articleCommentNo.eq(articlePK)).
				execute();
	}
	

}
