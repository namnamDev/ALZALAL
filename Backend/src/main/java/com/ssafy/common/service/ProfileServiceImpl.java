package com.ssafy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.member.Member;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.MemberRepository;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private MemberRepository memberRepository;

	
	//프로필 이미지 설정
	@Override
	public void setProfileImg(String profileImgUri) {
		Member member=memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(()->new IllegalStateException("로그인 유저정보가 없습니다"));
	
		member.setProfileImg(profileImgUri);
		return ;
	}

	
	//프로필 한줄소개 설정
	@Override
	public void setProfileIntoduce(String introduce) {
		Member member=memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(()->new IllegalStateException("로그인 유저정보가 없습니다"));
		
		member.setIntroduce(introduce);
		return ;
	}
}
