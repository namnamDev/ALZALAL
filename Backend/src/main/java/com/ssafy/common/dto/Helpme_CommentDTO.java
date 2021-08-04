package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.ssafy.common.domain.helpme.Helpme_Class;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Helpme_CommentDTO {
	private Long helpmeCommentNo;
	private long helpmeNo;
	private MemberDTO member;
	//요청 받은사람
	private String content;
	private LocalDateTime helpmeCommentDate;
	

	// 좋아요 갯수
	private Long likeCount;
	// 좋아요 여부
	private Boolean likeState;

	// 댓글 갯수

	//likeState를 갯수로 받아와서 생성자에서 boolean으로 변환시켜줌
	public Helpme_CommentDTO(
			Long helpmeCommentNo,
			Long helpmeNo,
			MemberDTO member,
			String helpmeCommentContent,
			Long likeCount,
			Long likeState,
			LocalDateTime helpmeCommentDate
			) {
			super();
			this.helpmeCommentNo = helpmeCommentNo;
			this.helpmeNo = helpmeNo;
			this.member = member;
			this.content = helpmeCommentContent;
			this.likeCount = likeCount==null?0:likeCount;
			this.likeState = likeState>0?true:false;
			this.helpmeCommentDate = helpmeCommentDate;
		}
	
	
	
}
