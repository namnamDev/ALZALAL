package com.ssafy.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.helpme.QHelpme;
import com.ssafy.common.domain.helpme.QHelpme_Comment;
import com.ssafy.common.domain.helpme.QHelpme_Like;
import com.ssafy.common.dto.HelpmeDTO;
import com.ssafy.common.dto.MemberDTO;
import com.ssafy.common.dto.ProblemSiteDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HelpmeRepositoryImpl implements HelpmeRepositoryCustom {

	// @RequiredArgsConstructor를 통한 생성자 인젝션
	private final JPAQueryFactory jpaQueryFactory;

	// memberNo가 받은 문제풀이 요청 목록
	@Override
	public Optional<List<HelpmeDTO>> getReceptHelpmeListByMemberNo(Long memberNo, Long nowLoginMemberNo, Pageable page) {
		QHelpme h = QHelpme.helpme;
		QHelpme_Comment hc = QHelpme_Comment.helpme_Comment;
		QHelpme_Like hl = QHelpme_Like.helpme_Like;

		List<HelpmeDTO> ret = jpaQueryFactory
				.select(Projections.constructor(HelpmeDTO.class, h.helpmeNo,
						Projections.constructor(ProblemSiteDTO.class,
								h.problemSite),
						Projections.constructor(MemberDTO.class,
								h.helpmeSenderNo.name, h.helpmeSenderNo.no),
						Projections.constructor(MemberDTO.class,
								h.helpmeReceptorNo.name, h.helpmeReceptorNo.no),
						h.helpmeContent, h.helpmeStatus, h.helpmeDate,
						ExpressionUtils.as(JPAExpressions// 좋아요 갯수
								.select(hl.count()).from(hl)
								.where(hl.helpmeNo.helpmeNo.eq(h.helpmeNo)),
								"likeCount"),
						ExpressionUtils.as(JPAExpressions// nowLoginMemberNo의 좋아요 여부
								.select(hl.count()).from(hl)
								.where(hl.helpmeNo.helpmeNo.eq(h.helpmeNo).and(
										hl.member.no.eq(nowLoginMemberNo))),
								"likeState"),
						ExpressionUtils.as(JPAExpressions// 댓글 갯수
								.select(hc.count()).from(hc)
								.where(hc.helpmeNo.helpmeNo.eq(h.helpmeNo)),
								"likeState")))
				.from(h)
				.where(h.helpmeReceptorNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return Optional.of(ret);
	}

	// memberNo가 신청한 문제풀이 요청 목록
	// 이건 자기 프로필에서만 보여지는거니까 memberNo, nowLoginMemberNo가 동일인물임
	@Override
	public Optional<List<HelpmeDTO>> getSendHelpmeListByMemberNo(Long memberNo, Pageable page) {
		QHelpme h = QHelpme.helpme;
		QHelpme_Comment hc = QHelpme_Comment.helpme_Comment;
		QHelpme_Like hl = QHelpme_Like.helpme_Like;

		List<HelpmeDTO> ret = jpaQueryFactory
				.select(Projections.constructor(HelpmeDTO.class, h.helpmeNo,
						Projections.constructor(ProblemSiteDTO.class,
								h.problemSite),
						Projections.constructor(MemberDTO.class,
								h.helpmeSenderNo.name, h.helpmeSenderNo.no),
						Projections.constructor(MemberDTO.class,
								h.helpmeReceptorNo.name, h.helpmeReceptorNo.no),
						h.helpmeContent, h.helpmeStatus, h.helpmeDate,
						ExpressionUtils.as(JPAExpressions// 좋아요 갯수
								.select(hl.count()).from(hl)
								.where(hl.helpmeNo.helpmeNo.eq(h.helpmeNo)),
								"likeCount"),
						ExpressionUtils.as(JPAExpressions// memberNo의 좋아요 여부
								.select(hl.count()).from(hl)
								.where(hl.helpmeNo.helpmeNo.eq(h.helpmeNo).and(
										hl.member.no.eq(memberNo))),
								"likeState"),
						ExpressionUtils.as(JPAExpressions// 댓글 갯수
								.select(hc.count()).from(hc)
								.where(hc.helpmeNo.helpmeNo.eq(h.helpmeNo)),
								"likeState")))
				.from(h)
				.where(h.helpmeSenderNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return Optional.of(ret);
	}
}
