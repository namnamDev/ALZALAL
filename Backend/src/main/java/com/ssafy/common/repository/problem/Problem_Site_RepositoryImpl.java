package com.ssafy.common.repository.problem;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;

import lombok.RequiredArgsConstructor;

import static com.ssafy.common.domain.problem.QProblem_Site.*;

@RequiredArgsConstructor
@Repository
public class Problem_Site_RepositoryImpl implements Problem_Site_RepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Problem_Site sltOneProblem(Problem_Site_List problemSite,long No) {
		return queryFactory.selectFrom(problem_Site).
				where(problem_Site.problemSiteName.eq(problemSite)).
				where(problem_Site.problemNo.eq(No)).
				fetchOne();
	}
}
