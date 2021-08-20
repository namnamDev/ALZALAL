package com.ssafy.common.domain.problem;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.member.Member;

import lombok.Getter;
import lombok.Setter;



@Entity
@Getter @Setter
@IdClass(Problem_Site_LikePK.class)
public class Problem_Site_Like {

	//Problem_Site_List 도메인과 관계 
	@Id
	@ManyToOne
	@JoinColumn(name="problem_site_name")
	private Problem_Site_List problemSiteName;
	
	//Member 도메인과 관계
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_no")
	private Member memberNo;
}

