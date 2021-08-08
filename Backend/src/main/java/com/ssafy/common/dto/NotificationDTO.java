package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.ssafy.common.domain.Notification_SubTask;
import com.ssafy.common.domain.Notification_Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
	
	private long notiNo;
	private long notiReciever;
	private boolean notiReadStatus;
	private long notiTargetNo;
	private Notification_Task notiTaskClass;
	private Notification_SubTask notiSubTaskClass;
	private LocalDateTime notiDate;
}
