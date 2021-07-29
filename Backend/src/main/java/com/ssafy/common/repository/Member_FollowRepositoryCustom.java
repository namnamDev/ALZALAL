package com.ssafy.common.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;

public interface Member_FollowRepositoryCustom {


	// memborNo를 팔로우 하고 있는 사람들(팔로워)
	List<Tuple> getFollowers(Long memberNo,Pageable page);
	
	// memberNo가 팔로우 한 사람들(사람 팔로잉)
	List<Tuple> getMemberFollowings(Long memberNo,Pageable page);
	

}
