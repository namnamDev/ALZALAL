package com.ssafy.common.repository.problem;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.problem.Problem_Follow;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
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
	
	//problem으로 현재 팔로잉 몇개인지 카운트
	@Override
	public long countProblemFollowings(String problemSite, long problemNo) {
		QProblem_Follow pf=QProblem_Follow.problem_Follow;
		
		long result=jpaQueryFactory.selectFrom(pf)
					.where(pf.followingProblemSiteName.problemSiteName.problemSiteName.eq(problemSite)
							,pf.followingProblemSiteName.problemNo.eq(problemNo))
					.fetchCount();
		
		return result;
	}
	
	//memberNo가 problem을 팔로우 중인지 아닌지
	@Override
	public boolean isProblemFollowing(long memberNo, Problem_Site problemSite) {
		QProblem_Follow pf=QProblem_Follow.problem_Follow;
		
		long result=jpaQueryFactory.select(pf).from(pf)
					.where(pf.followingProblemSiteName.eq(problemSite),pf.member.no.eq(memberNo))
					.fetchCount();
		
		if(result>0)
			return true;
		else 
			return false;
	}
	
}
