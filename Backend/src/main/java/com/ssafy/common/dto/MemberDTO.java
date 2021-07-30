package com.ssafy.common.dto;



import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MemberDTO {
	private String name;
	private long no;
	@QueryProjection
	public MemberDTO(String name, long no) {
		this.name = name;
		this.no = no;
	}
}
