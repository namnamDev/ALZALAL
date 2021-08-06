package com.ssafy.common.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.common.domain.member.Member;
import com.ssafy.common.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final MemberRepository memberRepository;
	
	//멤버 세션id 저장 후 알림 갯수 가져옴
	public Long setMemberSessionId(long memberNo, String sessionId) {
		//로그인중인 유저정보 가져옴
		Member member = memberRepository.findByNo(memberNo)
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
		member.setSessionId(sessionId);
		return member.getNotificationCount();
	}
	
}
