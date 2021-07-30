package com.ssafy.common.dto;

import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProblemSiteDTO {
	private String problemSiteName;
	private Long problemNo;
	private String problemSiteLink;
	
	public ProblemSiteDTO(Problem_Site_List problemSiteName, Long problemNo,
			String problemSiteLink) {
		this.problemSiteName = problemSiteName.getProblemSiteName();
		this.problemNo = problemNo;
		this.problemSiteLink = problemSiteLink;
	}
	
	public ProblemSiteDTO(Problem_Site problem_Site) {
		this.problemSiteName = problem_Site.getProblemSiteName().getProblemSiteName();
		this.problemNo = problem_Site.getProblemNo();
		this.problemSiteLink = problem_Site.getProblemSiteLink();
	}
	
	
	
}
