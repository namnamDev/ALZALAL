package com.ssafy.common.dto;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Discuss_CommentDTO {
	private Long discussCommentNo;
	private Long discussNo;
	private MemberDTO member;
	private String content;
	private Long likeCount;
	private Boolean likeState;
	private LocalDateTime discussCommentDate;
	
	public Discuss_CommentDTO(
		Long discussCommentNo,
		Long discussNo,
		MemberDTO member,
		String content,
		Long likeCount,
		Long likeState,
		LocalDateTime discussCommentDate
		) {
		super();
		this.discussCommentNo = discussCommentNo;
		this.discussNo = discussNo;
		this.member = member;
		this.content = content;
		this.likeCount = likeCount==null?0:likeCount;
		this.likeState = likeState>0?true:false;
		this.discussCommentDate = discussCommentDate;
	}
//	private Long likeCount;
//	private Boolean likeState;
}
