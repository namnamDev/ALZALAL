package com.ssafy.common.dto;



import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
public class Discuss_HostDTO {
	private String name;
	private long no;
	@QueryProjection
	public Discuss_HostDTO(Long no,String name) {
		this.name = name;
		this.no = no;
	}
}
