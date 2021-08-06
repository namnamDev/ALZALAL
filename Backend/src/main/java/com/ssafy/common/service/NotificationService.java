package com.ssafy.common.service;

public interface NotificationService {

	//멤버 세션id 저장 후 알림 갯수 가져옴
	public Long setMemberSessionId(long memberNo, String sessionId);
	
}
