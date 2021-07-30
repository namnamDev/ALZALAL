package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.dto.MemberDTO;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.QArticle_Comment;
import com.ssafy.common.domain.article.QArticle_Comment_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.member.QMember;
@Repository
@RequiredArgsConstructor
public class Article_CommentRepositoryImpl implements Article_CommentRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	
	//댓글 다건조회
	@Override
	public Optional<List<Article_CommentDTO>> artiComments(Article article,Long memberNo,Pageable page){
		QArticle_Comment arCo = QArticle_Comment.article_Comment;
		QArticle_Comment_Like arCoLi= QArticle_Comment_Like.article_Comment_Like;
		List<Article_CommentDTO>res = queryFactory.select(Projections.fields(Article_CommentDTO.class
						,arCo.articleCommentNo
						,arCo.articleNo
						,Projections.constructor(MemberDTO.class
								,arCo.member.no
								,arCo.member.name)
						,arCo.articleContent
						,ExpressionUtils.as(
								JPAExpressions.select(arCo.count())
								.from(arCoLi)
								.where(arCoLi.articleComment.articleCommentNo.eq(arCo.articleCommentNo))
						,"likeCount")
						,ExpressionUtils.as(
								JPAExpressions.select(arCoLi.count())
								.from(arCoLi)
								.where(arCoLi.member.no.eq(memberNo).and(arCoLi.articleComment.articleCommentNo.eq(arCo.articleCommentNo)))
								,"likeState"
								)
						))
				.from(arCo)
				.where(arCo.articleNo.eq(article))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();
		return Optional.of(res);
	}
//게시글의 댓글 조건조회
	@Override 
	public Article_CommentDTO articleComment(Article_Comment ArtiCo,Member member) {
		QArticle_Comment arCo = QArticle_Comment.article_Comment;
		QMember m = QMember.member;
		Article_CommentDTO dto = queryFactory
				.select(
				Projections.constructor(
						Article_CommentDTO.class,
						arCo.articleCommentNo,
						arCo.articleNo,
						Projections.constructor(
								MemberDTO.class,
								arCo.member.no,
								arCo.member.name),
						arCo.articleContent
						)).from(arCo)
							.fetchFirst();
		System.out.println(dto);
		return null;
	}
	
	public Article_CommentDTO arts(Article_Comment ArtiCo,Member member) {
		QArticle_Comment arCo = QArticle_Comment.article_Comment;
		QMember m = QMember.member;
		QArticle_Comment_Like arCoLi = QArticle_Comment_Like.article_Comment_Like;
		Article_CommentDTO dto = queryFactory
				.select(
				Projections.constructor(
						Article_CommentDTO.class,
						arCo.articleCommentNo,
						arCo.articleNo,
						Projections.constructor(
								MemberDTO.class,
								arCo.member.no,
								arCo.member.name),
						arCo.articleContent,
						ExpressionUtils.as(
								JPAExpressions.select(arCo.articleCommentNo.count())
								.from(arCoLi)
								.where(arCoLi.articleComment.articleCommentNo.eq(arCo.articleCommentNo))
						,"likeCount")
						,ExpressionUtils.as(
								JPAExpressions.select(arCo.articleCommentNo)
								.from(arCoLi)
								.where(arCoLi.member.eq(member)).exists()
								,"likeState"
								)
						)).from(arCo)
							.fetchFirst();
		System.out.println(dto);
		return null;
	}
}
