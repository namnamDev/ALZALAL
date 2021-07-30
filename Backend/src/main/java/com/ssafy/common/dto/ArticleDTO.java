package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.ssafy.common.domain.article.Article_Class;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleDTO {
	private Long articleNo;

	private MemberDTO member;
	
	private String articleTitle;
	private String articleContent;
	private LocalDateTime articleDate;

	private ProblemSiteDTO problemSite;

	private String useLanguage;
	private Article_Class articleClass;
	
	//좋아요 갯수
	private Long likeCount;
	private Boolean likeState; 
	
	//댓글 갯수
	private Long commentCount;

	public ArticleDTO(Long articleNo, MemberDTO member, String articleTitle,
			String articleContent, LocalDateTime articleDate,
			ProblemSiteDTO problemSite, String useLanguage,
			Article_Class articleClass, Long likeCount, Long likeState,
			Long commentCount) {
		super();
		this.articleNo = articleNo;
		this.member = member;
		this.articleTitle = articleTitle;
		this.articleContent = articleContent;
		this.articleDate = articleDate;
		this.problemSite = problemSite;
		this.useLanguage = useLanguage;
		this.articleClass = articleClass;
		this.likeCount = likeCount;
		this.likeState = likeState>0?true:false;
		this.commentCount = commentCount;
	}




	
	
	
	
}
