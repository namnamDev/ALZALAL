package com.ssafy.common.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.common.domain.Notification;
import com.ssafy.common.domain.Notification_SubTask;
import com.ssafy.common.domain.Notification_Task;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.NotificationSocketDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.NotificationRepository;
import com.ssafy.common.repository.member.MemberRepository;
import com.ssafy.common.websocket.NotificationSender;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final MemberRepository memberRepository;

	private final NotificationRepository notificationRepository;
	
	private final NotificationSender notificationSender;
	
	//멤버 세션id 저장 후 알림 갯수 가져옴
	@Override
	public Long setMemberSessionId(long memberNo, String sessionId) {
		//로그인중인 유저정보 가져옴
		Member member = memberRepository.findByNo(memberNo)
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
		member.setSessionId(sessionId);
		return member.getNotificationCount();
	}
	
	//회원 팔로우 알림 저장후 알림 갯수 소켓통신으로 전송
	@Override
	public void followMember(long receiverNo) {
		Member sender=memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		Member receiver=memberRepository.findById(receiverNo)
				.orElseThrow(() -> new IllegalStateException("존재하지 않는 팔로우 대상입니다"));
		
		Notification notification=new Notification();
		notification.setNotificationSender(sender);//팔로우 하는사람
		notification.setNotificationReciever(receiver);//팔로우 당하는사람
		notification.setNotificationReadStatus(false);//안읽은 상태로 설정 
		notification.setNotificationTargetNO(receiverNo);//팔로우 당하는사람 pk
		notification.setNotificationTask(Notification_Task.FOL);//팔로우
		notification.setNotificationSubTask(Notification_SubTask.NMF);//유저 팔로우
		// 1. 알림 등록
		notificationRepository.save(notification);
		
		// 2. member테이블의 notificationCount 1증가
		long notificationCount = receiver.getNotificationCount()+1;
		receiver.setNotificationCount(notificationCount);
		
		// 3. 알림 전송
		NotificationSocketDTO socketDTO=new NotificationSocketDTO();
		if(receiver.getNotificationCount()>0) {
			socketDTO.setNew(true);
			socketDTO.setCount(receiver.getNotificationCount());
		}
		//알림 전송
		notificationSender.sendNotification(receiver.getSessionId(), socketDTO);
		return;
	}
	
}
