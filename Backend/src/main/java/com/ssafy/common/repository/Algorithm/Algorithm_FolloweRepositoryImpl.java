package com.ssafy.common.repository.Algorithm;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.Algorithm_Follow;
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
	
	//algorithm으로 현재 팔로잉 몇개인지 카운트
	@Override
	public long countAlgorithmFollowings(String algorithmName) {
		
	QAlgorithm_Follow af=QAlgorithm_Follow.algorithm_Follow;
		
		long result = jpaQueryFactory.select(af)
				.from(af)
				.where(af.followingArgorithmNo.algorithmName.eq(algorithmName))
				.fetchCount();
		
		return result;
	}
	
	//memberNo가 algorithm을 팔로우 중인지 리턴
	@Override
	public boolean isAlgorithmFollowing(long memberNo, String algorithmName) {
		
	QAlgorithm_Follow af=QAlgorithm_Follow.algorithm_Follow;
		
		Algorithm_Follow result = jpaQueryFactory.select(af)
				.from(af)
				.where(af.followingArgorithmNo.algorithmName.eq(algorithmName),af.memberNo.no.eq(memberNo))
				.fetchFirst();
		
		if(result==null)
			return false;
		else 
			return true;
	}
	
}
