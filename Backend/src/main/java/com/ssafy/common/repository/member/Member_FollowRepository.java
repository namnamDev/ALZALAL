package com.ssafy.common.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.member.Member_Follow;
import com.ssafy.common.domain.member.Member_FollowPK;

public interface Member_FollowRepository extends JpaRepository<Member_Follow, Member_FollowPK>{

	// memberNo가 팔로우 한 사람들(팔로잉) 인원 카운트
	Long countByFollowNo(Member memberNo);

	// memborNo를 팔로우 하고 있는 사람들(팔로워) 인원 카운트
	Long countByMemberNo(Member memberNo);
	
	// memberNo가 followNo를 팔로우하고있는지 확인
	Long countByMemberNoAndFollowNo(Member memberNo,Member followNo);
}
