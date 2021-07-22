package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Problem_Site_LikePK implements Serializable{
	private String problem_site_name;
	private long member_no;
	//@Data안쓰면 손으로 써야하는 코드임.
	public Problem_Site_LikePK(String problem_site_name, long member_no) {
		this.problem_site_name = problem_site_name;
		this.member_no = member_no;
	}
	
	public Problem_Site_LikePK() {}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Problem_Site_LikePK other = (Problem_Site_LikePK) obj;
		if (member_no != other.member_no)
			return false;
		if (problem_site_name == null) {
			if (other.problem_site_name != null)
				return false;
		} else if (!problem_site_name.equals(other.problem_site_name))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (member_no ^ (member_no >>> 32));
		result = prime * result + ((problem_site_name == null) ? 0 : problem_site_name.hashCode());
		return result;
	}
}