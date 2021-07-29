package com.ssafy.common.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.problem.QProblem_Follow;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class Problem_FollowRepositoryImpl implements Problem_FollowRepositoryCustom{
	private final JPAQueryFactory jpaQueryFactory;
	
	// memberNo가 팔로잉 한 문제(문제 팔로잉)
	@Override
	public List<Tuple> getProblemFollowings(Long memberNo,Pageable page){
		QProblem_Follow pf=QProblem_Follow.problem_Follow;
		
		List<Tuple> result = jpaQueryFactory.select(pf.followingProblemSiteName.problemSiteName, pf.followingProblemSiteName.problemNo)
				.from(pf)
				.where(pf.member.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();
		
		return result;
	}
	
}
