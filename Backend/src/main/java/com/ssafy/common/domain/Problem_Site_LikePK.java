package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Problem_Site_LikePK implements Serializable{
	private String problemSiteName;
	private Long memberNo;
	
	public Problem_Site_LikePK(String problem_site_name, long member_no) {
		this.problemSiteName = problem_site_name;
		this.memberNo = member_no;
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
		if (memberNo != other.memberNo)
			return false;
		if (problemSiteName == null) {
			if (other.problemSiteName != null)
				return false;
		} else if (!problemSiteName.equals(other.problemSiteName))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (memberNo ^ (memberNo >>> 32));
		result = prime * result + ((problemSiteName == null) ? 0 : problemSiteName.hashCode());
		return result;
	}
}