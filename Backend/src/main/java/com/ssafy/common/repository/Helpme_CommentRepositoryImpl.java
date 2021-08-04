package com.ssafy.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import com.ssafy.common.dto.Helpme_CommentDTO;
import com.ssafy.common.dto.MemberDTO;
import com.ssafy.common.domain.helpme.Helpme_Comment;
import com.ssafy.common.domain.helpme.QHelpme_Comment;
import com.ssafy.common.domain.helpme.QHelpme_Comment_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.member.QMember;
@Repository
@RequiredArgsConstructor
public class Helpme_CommentRepositoryImpl implements Helpme_CommentRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	
	//댓글 다건조회
	@Override
	public Optional<List<Helpme_CommentDTO>> artiComments(Long articleNo,Long memberNo,Pageable page){
		QHelpme_Comment arCo = QHelpme_Comment.helpme_Comment;
		QHelpme_Comment_Like arCoLi= QHelpme_Comment_Like.helpme_Comment_Like;
		List<Helpme_CommentDTO>res = queryFactory.select(Projections.constructor(Helpme_CommentDTO.class
						,arCo.helpmeCommentNo
						,arCo.helpmeNo.helpmeNo
						,Projections.constructor(MemberDTO.class,arCo.member.name,arCo.member.no)
						,arCo.helpmeCommentContent
						,ExpressionUtils.as(
								JPAExpressions.select(arCoLi.count())
								.from(arCoLi)
								.where(arCoLi.helpmeCommentNo.helpmeCommentNo.eq(arCo.helpmeCommentNo))
						,"likeCount")
						,ExpressionUtils.as(
								JPAExpressions.select(arCoLi.count())
								.from(arCoLi)
								.where(arCoLi.member.no.eq(memberNo).and(arCoLi.helpmeCommentNo.helpmeCommentNo.eq(arCo.helpmeCommentNo)))
						,"likeState")
						,arCo.helpmeCommentDate
						))
				.from(arCo)
				.where(arCo.helpmeNo.helpmeNo.eq(articleNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();
		return Optional.of(res);
	}
//게시글의 댓글 조건조회
	@Override 
	public Helpme_CommentDTO articleComment(Helpme_Comment ArtiCo,Member member) {
		QHelpme_Comment arCo = QHelpme_Comment.helpme_Comment;
		QMember m = QMember.member;
		Helpme_CommentDTO dto = queryFactory
				.select(
				Projections.constructor(
						Helpme_CommentDTO.class,
						arCo.helpmeCommentNo,
						arCo.helpmeNo,
						Projections.constructor(
								MemberDTO.class,
								arCo.member.no,
								arCo.member.name),
						arCo.helpmeCommentContent
						)).from(arCo)
							.fetchFirst();
		System.out.println(dto);
		return null;
	}
	@Override
	public Helpme_Comment sltOneArtiCom(Long commentPK){
		QHelpme_Comment arCo = QHelpme_Comment.helpme_Comment;
		return queryFactory.selectFrom(arCo)
				.where(arCo.helpmeCommentNo.eq(commentPK))
				.fetchFirst();
	}
	
	@Override
	public long artiComDelete(Long commentPK){
		QHelpme_Comment arCo = QHelpme_Comment.helpme_Comment;
		return queryFactory.delete(arCo)
				.where(arCo.helpmeCommentNo.eq(commentPK))
				.execute();
	}
	
	@Override
	public long artiComUpdate(Long commentPK,String content){
		QHelpme_Comment arCo = QHelpme_Comment.helpme_Comment;
		return queryFactory.update(arCo)
				.where(arCo.helpmeCommentNo.eq(commentPK))
				.set(arCo.helpmeCommentContent, content)
				.execute();
	}
//	@Override
//	public long articleDelete(Long)
}
