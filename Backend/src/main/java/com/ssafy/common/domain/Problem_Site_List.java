package com.ssafy.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Problem_Site_List {
	
	@Id
	private String problem_site_name;
	
	//Problem_Site_Like 도메인과 관계
	@OneToMany(mappedBy = "problem_site_list")
	private List<Problem_Site_Like> member=new ArrayList<>();
}
