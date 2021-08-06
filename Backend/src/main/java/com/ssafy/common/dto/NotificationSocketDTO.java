package com.ssafy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSocketDTO {
	// 새로운게 있는지 여부
	private boolean isNew;
	
	//알람 갯수
	private int count;	
	
	//받는사람
	private String to;
}
