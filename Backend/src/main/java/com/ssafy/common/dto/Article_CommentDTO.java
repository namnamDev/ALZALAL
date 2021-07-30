package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.common.domain.article.Article_Class;

import lombok.Builder;
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
	private boolean myLike;
	
	@QueryProjection
	public Article_CommentDTO(
		Long articleCommentNo,
		Long articleNo,
		MemberDTO memberDTO,
		String content,
		Long likeCount,
		Boolean myLike
		) {
		this.articleCommentNo = articleCommentNo;
		this.articleNo = articleNo;
		this.memberDTO = memberDTO;
		this.content = content;
		this.likeCount = likeCount;
		this.myLike = myLike;
	}
//	private Long likeCount;
//	private Boolean likeState;
}
