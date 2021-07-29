package com.ssafy.common.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.QAlgorithm_Follow;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class Algorithm_FolloweRepositoryImpl implements Algorithm_FolloweRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	
	// memberNo가 팔로잉 한 알고리즘(알고리즘 팔로잉)
	@Override
	public List<String> getAlgorithmFollowings(Long memberNo,Pageable page){
		QAlgorithm_Follow af=QAlgorithm_Follow.algorithm_Follow;
		
		List<String> result = jpaQueryFactory.select(af.followingArgorithmNo.algorithmName)
				.from(af)
				.where(af.memberNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();
		
		return result;
	}
	
}
