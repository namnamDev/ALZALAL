package com.ssafy.common.repository.Algorithm;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface Algorithm_FolloweRepositoryCustom {
	
	List<String> getAlgorithmFollowings(Long memberNo,Pageable page);
	
	//algorithm으로 현재 팔로잉 몇개인지 카운트
	long countAlgorithmFollowings(String algorithmName);
	
	//memberNo가 algorithm을 팔로우 중인지 리턴
	boolean isAlgorithmFollowing(long memberNo, String algorithmName);
}
