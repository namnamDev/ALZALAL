package com.ssafy.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Problem_Site_List {
	
	@Id
	@Column(name="PROBLEM_SITE_NAME")
	private String problem_site_name;
	
	
	// Problem_Site_List에서 회원에 접근할 필요가 있는지 모르겠음 일단 주석처리해둠 필요하면 나중에 주석 풀어서 쓰면될듯
	// ex) 백준을 선호하는 사람들을 찾는 경우가 있을까? 있다면 이거 주석 해제해서 쓰면댐
//	//Problem_Site_Like 도메인과 관계
//	@OneToMany(mappedBy = "problem_site_name")
//	private List<Problem_Site_Like> member=new ArrayList<>();
}
