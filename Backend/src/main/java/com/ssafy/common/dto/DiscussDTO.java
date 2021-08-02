package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.discuss.Discuss_Host;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiscussDTO {
	private Long discussNo;
	
	private Discuss_HostDTO discussCompHostNo;
	private String discussCompName;
	private LocalDateTime DiscussDate;

	private String disscussCompProblem;
	
	
	//댓글 갯수
	private Long commentCount;

	public DiscussDTO(
			Long discussNo,
			Discuss_HostDTO discussCompHostNo,
			String discussCompName,
			String disscussCompProblem,
			LocalDateTime DiscussDate,
			Long commentCount) {
		super();
		this.discussNo = discussNo;
		this.discussCompHostNo=discussCompHostNo;
		this.discussCompName =discussCompName;
		this.DiscussDate=DiscussDate;
		this.disscussCompProblem = disscussCompProblem;
		this.commentCount = commentCount;
	}




	
	
	
	
}
