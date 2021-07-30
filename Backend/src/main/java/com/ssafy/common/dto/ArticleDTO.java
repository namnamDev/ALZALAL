package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.ssafy.common.domain.article.Article_Class;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class ArticleDTO {
	private Long articleNo;

	private memberDTO member;
	
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
}
