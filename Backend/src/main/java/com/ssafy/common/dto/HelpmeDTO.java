package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.ssafy.common.domain.helpme.Helpme_Class;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelpmeDTO {
	private long helpmeNo;
	private ProblemSiteDTO problemSite;
	//요청 한사람
	private MemberDTO helpmeSenderNo;
	//요청 받은사람
	private MemberDTO helpmeReceptorNo;
	private String helpmeContent;
	private Helpme_Class helpmeStatus;
	private LocalDateTime helpmeDate;

	// 좋아요 갯수
	private Long likeCount;
	// 좋아요 여부
	private Boolean likeState;

	// 댓글 갯수
	private Long commentCount;

	//likeState를 갯수로 받아와서 생성자에서 boolean으로 변환시켜줌
	public HelpmeDTO(long helpmeNo, ProblemSiteDTO problemSite,
			MemberDTO helpmeSenderNo, MemberDTO helpmeReceptorNo,
			String helpmeContent, Helpme_Class helpmeStatus,
			LocalDateTime helpmeDate, Long likeCount, Long likeState,
			Long commentCount) {
		super();
		this.helpmeNo = helpmeNo;
		this.problemSite = problemSite;
		this.helpmeSenderNo = helpmeSenderNo;
		this.helpmeReceptorNo = helpmeReceptorNo;
		this.helpmeContent = helpmeContent;
		this.helpmeStatus = helpmeStatus;
		this.helpmeDate = helpmeDate;
		this.likeCount = likeCount;
		this.likeState = likeState>0?true:false;
		this.commentCount = commentCount;
	}	
	
	
	
}
