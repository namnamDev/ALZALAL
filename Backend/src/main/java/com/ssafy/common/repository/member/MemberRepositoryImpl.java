package com.ssafy.common.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.article.QArticle;
import com.ssafy.common.domain.member.QMember;
import com.ssafy.common.domain.member.QMember_Follow;
import com.ssafy.common.dto.MemberSearchDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	
	//회원검색
	@Override
	public Optional<List<MemberSearchDTO>> getMemberSearch(String name,Long nowLoginMember,Pageable page) {
		QMember member=QMember.member;
		QMember_Follow memberFollow=QMember_Follow.member_Follow;
		QArticle article = QArticle.article;

		List<MemberSearchDTO> result= queryFactory.select(Projections.constructor(MemberSearchDTO.class, member.no,member.name,
						ExpressionUtils.as(JPAExpressions.select(memberFollow.count()).from(memberFollow).where(memberFollow.memberNo.eq(member)), "followingCount"),
						ExpressionUtils.as(JPAExpressions.select(memberFollow.count()).from(memberFollow).where(memberFollow.followNo.eq(member)), "followerCount"),
						ExpressionUtils.as(JPAExpressions.select(memberFollow.count()).from(memberFollow).where(memberFollow.memberNo.no.eq(nowLoginMember),memberFollow.followNo.no.eq(member.no)), "followState"),
						ExpressionUtils.as(JPAExpressions.select(article.count()).from(article).where(article.member.eq(member)), "articleCount")						
						))					
					.from(member)
					.where(member.name.like(name+"%"))
					.offset(page.getOffset())
					.limit(page.getPageSize())
					.orderBy(member.name.asc())
					.fetch();

		if(result==null)
			return null;
		
		return Optional.of(result);
	}
	
}
