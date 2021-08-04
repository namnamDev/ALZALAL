package com.ssafy.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowMemberDto {

	private Long no;
	private String name;
	private boolean followState;
	public FollowMemberDto(Long no, String name, Long followState) {
		super();
		this.no = no;
		this.name = name;
		this.followState = followState>0?true:false;
	}	
}
