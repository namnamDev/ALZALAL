package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Problem_Site_LikePK implements Serializable{
	private Problem_Site_List problem_site_list;
	private Member member;
	
	public Problem_Site_LikePK() {}
	
	public Problem_Site_LikePK(Problem_Site_List problem_site_list, Member member) {
		this.problem_site_list = problem_site_list;
		this.member = member;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Problem_Site_LikePK other = (Problem_Site_LikePK) obj;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (problem_site_list == null) {
			if (other.problem_site_list != null)
				return false;
		} else if (!problem_site_list.equals(other.problem_site_list))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((problem_site_list == null) ? 0 : problem_site_list.hashCode());
		return result;
	}


}