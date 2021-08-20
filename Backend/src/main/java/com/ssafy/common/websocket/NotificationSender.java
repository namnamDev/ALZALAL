package com.ssafy.common.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.ssafy.common.dto.Discuss_CommentDTO;
import com.ssafy.common.dto.NotificationSocketDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Component
@Log4j2
public class NotificationSender {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	public void sendNotification(String sessionId, NotificationSocketDTO socketDTO) {
		if(sessionId==null) {
			log.info("sessionId가 존재하지 않습니다");
			return;
		}
		simpMessagingTemplate.convertAndSendToUser(sessionId,
				"/notification/count", socketDTO);
	}
	
	public void sendDisscussComment(long discussNo, Discuss_CommentDTO discussComentDTO) {
		System.out.println("discussNo "+discussNo);
		simpMessagingTemplate.convertAndSend("/discuss/" + discussNo,discussComentDTO);
	}
	
}
