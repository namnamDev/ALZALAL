package com.ssafy.common.repository.Algorithm;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;

public interface Algorithm_FolloweRepositoryCustom {
	
	List<String> getAlgorithmFollowings(Long memberNo,Pageable page);
	
	//algorithm으로 현재 팔로잉 몇개인지 카운트
	public long countAlgorithmFollowings(String algorithmName);
}
