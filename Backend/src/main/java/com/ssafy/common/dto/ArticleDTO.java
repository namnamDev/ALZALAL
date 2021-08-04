package com.ssafy.common.dto;

import java.time.LocalDateTime;
import java.util.List;

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
	private List<Article_AlgorithmDTO> algo;
	
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


	//article에 oneToMany관계 맺은뒤 타입형이 맞지않아서 생성자 다시 생성했습니다
	//위의 생성자 필요없어지면 지워도 될것같습니다
	public ArticleDTO(Long articleNo, MemberDTO member, String articleTitle,
			String articleContent, LocalDateTime articleDate,
			ProblemSiteDTO problemSite, String useLanguage,
			Article_Class articleClass, int likeCount, Long likeState,
			int commentCount) {
		super();
		this.articleNo = articleNo;
		this.member = member;
		this.articleTitle = articleTitle;
		this.articleContent = articleContent;
		this.articleDate = articleDate;
		this.problemSite = problemSite;
		this.useLanguage = useLanguage;
		this.articleClass = articleClass;
		this.likeCount = (long)likeCount;
		this.likeState = likeState>0?true:false;
		this.commentCount = (long)commentCount;
	}

	
	
	
	
}
