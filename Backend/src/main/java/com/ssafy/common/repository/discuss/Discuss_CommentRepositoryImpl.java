package com.ssafy.common.repository.discuss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import com.ssafy.common.dto.Discuss_CommentDTO;
import com.ssafy.common.dto.MemberDTO;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.QArticle_Comment;
import com.ssafy.common.domain.discuss.QDiscuss_Comment_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.member.QMember;
import com.ssafy.common.domain.discuss.Discuss_Comment;
import com.ssafy.common.domain.discuss.QDiscuss_Comment;
@Repository
@RequiredArgsConstructor
public class Discuss_CommentRepositoryImpl implements Discuss_CommentRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	//댓글 다건조회
	@Override
	public Optional<List<Discuss_CommentDTO>> artiComments(Long articleNo,Long memberNo,Pageable page){
		QDiscuss_Comment arCo = QDiscuss_Comment.discuss_Comment;
		QDiscuss_Comment_Like arCoLi= QDiscuss_Comment_Like.discuss_Comment_Like;
		List<Discuss_CommentDTO>res = queryFactory.select(Projections.constructor(Discuss_CommentDTO.class
						,arCo.discussCommentNo
						,arCo.discussNo.discussNo
						,Projections.constructor(MemberDTO.class,arCo.member.name,arCo.member.no)
						,arCo.discussCommentContent
						,ExpressionUtils.as(
								JPAExpressions.select(arCoLi.count())
								.from(arCoLi)
								.where(arCoLi.discussCommentNo.discussCommentNo.eq(arCo.discussCommentNo))
						,"likeCount")
						,ExpressionUtils.as(
								JPAExpressions.select(arCoLi.count())
								.from(arCoLi)
								.where(arCoLi.member.no.eq(memberNo).and(arCoLi.discussCommentNo.discussCommentNo.eq(arCo.discussCommentNo)))
						,"likeState")
						,arCo.discussCommentDate
						))
				.from(arCo)
				.where(arCo.discussNo.discussNo.eq(articleNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return Optional.of(res);
	}
//게시글의 댓글 조건조회
	@Override 
	public Discuss_CommentDTO articleComment(Article_Comment ArtiCo,Member member) {
		QDiscuss_Comment arCo = QDiscuss_Comment.discuss_Comment;
		QMember m = QMember.member;
		Discuss_CommentDTO dto = queryFactory
				.select(
				Projections.constructor(
						Discuss_CommentDTO.class,
						arCo.discussCommentNo,
						arCo.discussNo,
						Projections.constructor(
								MemberDTO.class,
								arCo.member.no,
								arCo.member.name),
						arCo.discussCommentContent
						)).from(arCo)
							.fetchFirst();
		System.out.println(dto);
		return null;
	}
	@Override
	public Discuss_Comment sltOneArtiCom(Long commentPK){
		QDiscuss_Comment arCo = QDiscuss_Comment.discuss_Comment;
		return queryFactory.selectFrom(arCo)
				.where(arCo.discussCommentNo.eq(commentPK))
				.fetchFirst();
	}
	
	@Override
	public long artiComDelete(Long commentPK){
		QDiscuss_Comment arCo = QDiscuss_Comment.discuss_Comment;
		return queryFactory.delete(arCo)
				.where(arCo.discussCommentNo.eq(commentPK))
				.execute();
	}
	
	@Override
	public long artiComUpdate(Long commentPK,String content){
		QDiscuss_Comment arCo = QDiscuss_Comment.discuss_Comment;
		return queryFactory.update(arCo)
				.where(arCo.discussCommentNo.eq(commentPK))
				.set(arCo.discussCommentContent, content)
				.execute();
	}

}
