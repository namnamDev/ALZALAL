package com.ssafy.common.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Article_CommentDTO {
	private Long articleCommentNo;
	private Long articleNo;
	private MemberDTO memberDTO;
	private String content;
	private Long likeCount;
	private Boolean likeState;
	
	@QueryProjection
	public Article_CommentDTO(
		Long articleCommentNo,
		Long articleNo,
		MemberDTO memberDTO,
		String content,
		Long likeCount,
		Boolean likeState
		) {
		this.articleCommentNo = articleCommentNo;
		this.articleNo = articleNo;
		this.memberDTO = memberDTO;
		this.content = content;
		this.likeCount = likeCount;
		this.likeState = likeState;
	}
//	private Long likeCount;
//	private Boolean likeState;
}
