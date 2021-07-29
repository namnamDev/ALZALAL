package com.ssafy.common.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.member.QMember_Follow;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member_FollweRepositoryImpl implements Member_FollowRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	// memberNo를 팔로우 한 사람들(팔로워)
	@Override
	public List<Tuple> getFollowers(Long memberNo, Pageable page) {
		QMember_Follow mf = QMember_Follow.member_Follow;

		List<Tuple> result = queryFactory.select(mf.memberNo.no, mf.memberNo.name)
				.from(mf)
				.where(mf.followNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return result;
	}

	// memberNo가 팔로우 한 사람들(사람 팔로잉)
	@Override
	public List<Tuple> getMemberFollowings(Long memberNo, Pageable page) {
		QMember_Follow mf = QMember_Follow.member_Follow;

		List<Tuple> result = queryFactory.select(mf.followNo.no, mf.followNo.name)
				.from(mf)
				.where(mf.memberNo.no.eq(memberNo))	
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return result;
	}

}
