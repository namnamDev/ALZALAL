package com.ssafy.common.service;

public interface NotificationService {

	//멤버 세션id 저장 후 알림 갯수 가져옴
	Long setMemberSessionId(long memberNo, String sessionId);
	
	//회원 팔로우 알림 저장후 알림 갯수 소켓통신으로 전송
	void followMember(long receiverNo);
}
