package com.ssafy.common.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.common.dto.MemberDTO;
import com.ssafy.common.dto.NotificationSocketDTO;
import com.ssafy.common.service.NotificationService;
import com.ssafy.common.websocket.NotificationSender;

import lombok.RequiredArgsConstructor;

@Controller //소켓통신 받아오는거때매 rest안붙임
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationSender notificationSender;

	private final NotificationService notificationService;

	// 유저가 소켓서버에 연결되었을때 연결을 알리기위해 보낸 메세지에 매핑되는 곳으로
	// 유저의 sessionID를 디비에 저장시켜줌
	@MessageMapping("/connect")
	public void socketConnect(@Payload MemberDTO member,Principal principal) throws Exception {
		NotificationSocketDTO socketDTO = new NotificationSocketDTO();

		//알림 갯수 가져옴
		long notificationCount = notificationService
				.setMemberSessionId(member.getNo(), principal.getName());
		if (notificationCount > 0) {
			socketDTO.setNew(true);
			socketDTO.setCount(notificationCount);
		}
		
		//로그인한 유저에게 전송
		notificationSender.sendNotification(principal.getName(), socketDTO);
		return;
	}
	
	//유저의 알림 리스트 가져오기
//	@ResponseBody
//	@GetMapping("/list")
//	public void getNotificationList() {
//		
//	}
	
	
}
