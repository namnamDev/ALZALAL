package com.ssafy.common.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.common.domain.Notification;
import com.ssafy.common.domain.Notification_SubTask;
import com.ssafy.common.domain.Notification_Task;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.NotificationSocketDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.redis.RedisUtil;
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
	private final RedisUtil redisUtil;
	
	//멤버 세션id 저장 후 알림 갯수 가져옴
	@Override
	public Long setMemberSessionId(long memberNo, String sessionId) {
		//로그인중인 유저정보 가져옴
		Member member = memberRepository.findByNo(memberNo)
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
//		member.setSessionId(sessionId);
		long now = (new Date()).getTime();
		long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 3; //엑세스토큰처럼 만료기간 3시간으로 설정
		redisUtil.setDataExpire("notification"+member.getNo(),sessionId,new Date(now + ACCESS_TOKEN_EXPIRE_TIME).getTime());
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
		String sessionId=redisUtil.getData("notification"+receiverNo);
		//알림 전송
		notificationSender.sendNotification(sessionId, socketDTO);
		return;
	}
	
	
	//댓글 알림 저장후 알림 갯수 소켓통신으로 전송
	@Override
	public void articleComment(String articleClass,long articleNo,long receiverNo) {
		Member sender=memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		Member receiver=memberRepository.findById(receiverNo)
				.orElseThrow(() -> new IllegalStateException("존재하지 않는 팔로우 대상입니다"));
		
		Notification notification=new Notification();
		notification.setNotificationSender(sender);//댓글 적은사람
		notification.setNotificationReciever(receiver);//게시글 작성자
		notification.setNotificationReadStatus(false);//안읽은 상태로 설정 
		notification.setNotificationTargetNO(articleNo);//게시글 PK
		notification.setNotificationTask(Notification_Task.COM);//댓글
		if(articleClass.equals("article"))
			notification.setNotificationSubTask(Notification_SubTask.NAC);//게시글 종류(일반 게시글)
		else
			notification.setNotificationSubTask(Notification_SubTask.NHC);//게시글 종류(풀이요청 게시글)
		
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
		String sessionId=redisUtil.getData("notification"+receiverNo);
		//알림 전송
		notificationSender.sendNotification(sessionId, socketDTO);
		return;
	}
	
	//게시글좋아요 알림 저장후 알림 갯수 소켓통신으로 전송
	@Override
	public void LikeArticle(String articleClass,long articleNo,long receiverNo) {
		Member sender=memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		Member receiver=memberRepository.findById(receiverNo)
				.orElseThrow(() -> new IllegalStateException("존재하지 않는 팔로우 대상입니다"));
		
		Notification notification=new Notification();
		notification.setNotificationSender(sender);//좋아요 누른사람
		notification.setNotificationReciever(receiver);//게시글 작성자
		notification.setNotificationReadStatus(false);//안읽은 상태로 설정 
		notification.setNotificationTargetNO(articleNo);//게시글 PK
		notification.setNotificationTask(Notification_Task.LIK);//좋아요
		if(articleClass.equals("article"))
			notification.setNotificationSubTask(Notification_SubTask.NAL);//게시글 종류(일반 게시글)
		else
			notification.setNotificationSubTask(Notification_SubTask.NHL);//게시글 종류(풀이요청 게시글)
		
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
		String sessionId=redisUtil.getData("notification"+receiverNo);
		//알림 전송
		notificationSender.sendNotification(sessionId, socketDTO);
		return;
	}
	
	//댓글좋아요 알림 저장후 알림 갯수 소켓통신으로 전송
	@Override
	public void LikeComment(String articleClass,long articleNo,long receiverNo) {
		Member sender=memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		Member receiver=memberRepository.findById(receiverNo)
				.orElseThrow(() -> new IllegalStateException("존재하지 않는 팔로우 대상입니다"));
		
		Notification notification=new Notification();
		notification.setNotificationSender(sender);//좋아요 누른사람
		notification.setNotificationReciever(receiver);//댓글 작성자
		notification.setNotificationReadStatus(false);//안읽은 상태로 설정 
		notification.setNotificationTargetNO(articleNo);//좋아요 눌릿 댓글이 있는 게시글 PK
		notification.setNotificationTask(Notification_Task.LIK);//좋아요
		if(articleClass.equals("article"))
			notification.setNotificationSubTask(Notification_SubTask.NACL);//게시글 종류(일반 게시글)
		else if(articleClass.equals("helpme"))
			notification.setNotificationSubTask(Notification_SubTask.NHCL);//게시글 종류(풀이요청 게시글)
		else
			notification.setNotificationSubTask(Notification_SubTask.NDCL);//게시글 종류(토론 게시글)
		
		
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
		String sessionId=redisUtil.getData("notification"+receiverNo);
		//알림 전송
		notificationSender.sendNotification(sessionId, socketDTO);
		return;
	}
	
	//풀이요청게시글 응답 알림 저장후 알림 갯수 소켓통신으로 전송
	@Override
	public void helpmeChangeState(long helpmeNo,long receiverNo, Notification_SubTask subTask) {
		Member sender=memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		Member receiver=memberRepository.findById(receiverNo)
				.orElseThrow(() -> new IllegalStateException("작성자가 존재하지 않습니다"));
		
		Notification notification=new Notification();
		notification.setNotificationSender(sender);//풀이요청게시글 응답 누른사람
		notification.setNotificationReciever(receiver);//풀이요청게시글 작성자
		notification.setNotificationReadStatus(false);//안읽은 상태로 설정 
		notification.setNotificationTargetNO(helpmeNo);//풀이요청 게시글 PK
		notification.setNotificationTask(Notification_Task.HLP);//좋아요
		notification.setNotificationSubTask(subTask);//응답 종류

		
		
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
		String sessionId=redisUtil.getData("notification"+receiverNo);
		//알림 전송
		notificationSender.sendNotification(sessionId, socketDTO);
		return;
	}
}
