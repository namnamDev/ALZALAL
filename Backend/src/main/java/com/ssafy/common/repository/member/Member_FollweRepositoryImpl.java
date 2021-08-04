package com.ssafy.common.repository.member;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.member.QMember_Follow;
import com.ssafy.common.dto.FollowMemberDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member_FollweRepositoryImpl
		implements
			Member_FollowRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	// memberNo를 팔로우 한 사람들(팔로워)
	@Override
	public List<FollowMemberDto> getFollowers(Long memberNo,
			Long nowLoginMemberNo, Pageable page) {
		QMember_Follow mf1 =new QMember_Follow("mf1");
		QMember_Follow mf2 =new QMember_Follow("mf2");


		List<FollowMemberDto> result = queryFactory
				.select(Projections.constructor(FollowMemberDto.class,
						mf1.memberNo.no, mf1.memberNo.name,
						ExpressionUtils.as(JPAExpressions
								.select(mf2.count())
								.from(mf2)
								.where(mf2.memberNo.no.eq(nowLoginMemberNo),
										mf2.followNo.no.eq(mf1.memberNo.no)),
								"followState")))
				.from(mf1)
				.where(mf1.followNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return result;
	}

	// memberNo가 팔로우 한 사람들(사람 팔로잉)
	@Override
	public List<FollowMemberDto> getMemberFollowings(Long memberNo,
			Long nowLoginMemberNo, Pageable page) {
		QMember_Follow mf1 =new QMember_Follow("mf1");
		QMember_Follow mf2 =new QMember_Follow("mf2");

		List<FollowMemberDto> result = queryFactory
				.select(Projections.constructor(FollowMemberDto.class,
						mf1.followNo.no, mf1.followNo.name,
						ExpressionUtils.as(JPAExpressions
								.select(mf2.count())
								.from(mf2)
								.where(mf2.memberNo.no.eq(nowLoginMemberNo),
										mf2.followNo.no.eq(mf1.followNo.no)),
								"followState")))
				.from(mf1)
				.where(mf1.memberNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return result;
	}

}
