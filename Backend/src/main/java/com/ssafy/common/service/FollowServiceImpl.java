package com.ssafy.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.member.Member_Follow;
import com.ssafy.common.domain.member.Member_FollowPK;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.MemberRepository;
import com.ssafy.common.repository.Member_FollowRepository;

@Service
@Transactional
public class FollowServiceImpl implements FollowService{
	
	@Autowired
	private Member_FollowRepository member_FollowRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	//멤버 팔로우, 팔로우취소
	public Map<String,Object> memberFollow(Long memberNo) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Member followerMember = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
		if(memberNo==followerMember.getNo()) {
			throw new IllegalStateException("자기 자신을 팔로우 할 수 없습니다");
		}

		Member followingMemberNo=memberRepository.findByNo(memberNo)
				.orElseThrow(()-> new IllegalStateException("존재하지 않는 유저 입니다"));
		
		Member_FollowPK member_FollowPK=new Member_FollowPK(followerMember.getNo(), followingMemberNo.getNo());
		
		//이미 팔로우 중인지 확인
		if(member_FollowRepository.findById(member_FollowPK).isPresent()) {
			member_FollowRepository.deleteById(member_FollowPK);
			ret.put("success","True");
			ret.put("msg","팔로우 취소 완료");
			return ret;
		}
		
		Member_Follow member_Follow=new Member_Follow();
		
		//팔로우 하는사람 설정
		member_Follow.setMemberNo(followerMember);
		
		//팔로우 당하는사람 설정
		member_Follow.setFollowNo(followingMemberNo);
		
		member_FollowRepository.save(member_Follow);
		
		ret.put("success","True");
		ret.put("msg","팔로우 완료");
		return ret;
	}
	
}
