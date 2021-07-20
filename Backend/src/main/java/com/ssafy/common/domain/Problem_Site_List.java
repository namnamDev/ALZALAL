package com.ssafy.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Problem_Site_List {
	
	@Id
	String problem_site_name;
	
	@OneToMany(mappedBy = "problem_site_list")
	private List<Problem_Site_Like> member=new ArrayList<>();
	
}
