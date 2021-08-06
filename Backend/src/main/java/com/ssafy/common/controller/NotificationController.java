package com.ssafy.common.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ssafy.common.dto.NotificationSocketDTO;

@Controller
public class NotificationController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	
	@MessageMapping("/connect")
	public void sendSpecific(Principal principal,@Payload NotificationSocketDTO socketDTO,@Header("simpSessionId") String sessionId) throws Exception {
		System.out.println("socketDTO "+ socketDTO);
		System.out.println("Principal "+principal.getName());
		System.out.println("sessionID "+ sessionId);
		
		simpMessagingTemplate.convertAndSendToUser(socketDTO.getTo(),
				"/queue/test", socketDTO);
	}
}
