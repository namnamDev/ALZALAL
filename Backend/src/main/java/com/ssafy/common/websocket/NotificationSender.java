package com.ssafy.common.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.ssafy.common.dto.NotificationSocketDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NotificationSender {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	public void sendNotification(String sessionId, NotificationSocketDTO socketDTO) {
		simpMessagingTemplate.convertAndSendToUser(sessionId,
				"/notification/count", socketDTO);
	}
}
