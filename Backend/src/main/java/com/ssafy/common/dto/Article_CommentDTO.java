package com.ssafy.common.dto;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Article_CommentDTO {
	private Long articleCommentNo;
	private Long articleNo;
	private MemberDTO member;
	private String content;
	private Long likeCount;
	private Boolean likeState;
	private LocalDateTime articleCommentDate;
	
	public Article_CommentDTO(
		Long articleCommentNo,
		Long articleNo,
		MemberDTO member,
		String content,
		Long likeCount,
		Long likeState,
		LocalDateTime articleCommentDate
		) {
		super();
		this.articleCommentNo = articleCommentNo;
		this.articleNo = articleNo;
		this.member = member;
		this.content = content;
		this.likeCount = likeCount==null?0:likeCount;
		this.likeState = likeState>0?true:false;
		this.articleCommentDate = articleCommentDate;
	}
//	private Long likeCount;
//	private Boolean likeState;
}
