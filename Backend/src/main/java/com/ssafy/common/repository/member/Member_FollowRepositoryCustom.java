package com.ssafy.common.repository.member;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;
import com.ssafy.common.dto.FollowMemberDto;

public interface Member_FollowRepositoryCustom {


	// memborNo를 팔로우 하고 있는 사람들(팔로워)
	List<FollowMemberDto> getFollowers(Long memberNo,Long nowLoginMemberNo, Pageable page);
	
	// memberNo가 팔로우 한 사람들(사람 팔로잉)
	List<FollowMemberDto> getMemberFollowings(Long memberNo,Long nowLoginMemberNo,Pageable page);
	

}
