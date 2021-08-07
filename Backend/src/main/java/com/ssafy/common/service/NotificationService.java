package com.ssafy.common.service;

import com.ssafy.common.domain.Notification_SubTask;

public interface NotificationService {

	// 멤버 세션id 저장 후 알림 갯수 가져옴
	Long setMemberSessionId(long memberNo, String sessionId);

	// 회원 팔로우 알림 저장후 알림 갯수 소켓통신으로 전송
	void followMember(long receiverNo);

	// 댓글 알림 저장후 알림 갯수 소켓통신으로 전송
	void articleComment(String articleClass, long articleNo, long receiverNo);

	// 게시글좋아요 알림 저장후 알림 갯수 소켓통신으로 전송
	void LikeArticle(String articleClass, long articleNo, long receiverNo);

	// 댓글좋아요 알림 저장후 알림 갯수 소켓통신으로 전송
	void LikeComment(String articleClass, long articleNo, long receiverNo);
	
	//풀이요청게시글 응답 알림 저장후 알림 갯수 소켓통신으로 전송
	void helpmeChangeState(long helpmeNo,long receiverNo, Notification_SubTask subTask);
}
