package com.ssafy.common.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.problem.Problem_Site;

import lombok.RequiredArgsConstructor;

import static com.ssafy.common.domain.problem.QProblem_Site.*;

@RequiredArgsConstructor
@Repository
public class Problem_Site_Repository {
	private final JPAQueryFactory queryFactory;
	public Problem_Site sltOneProblem(String siteName,long No) {
		
		return queryFactory.selectFrom(problem_Site).
				where(problem_Site.problemSiteName.eq(siteName)).
				where(problem_Site.problemNo.eq(No)).
				fetchOne();
	}
}
