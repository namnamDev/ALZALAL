package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Algorithm_Like_Id implements Serializable {

	private Algorithm algorithm;
	private Member member;
	
	public Algorithm_Like_Id() {}

	public Algorithm_Like_Id(Algorithm algorithm, Member member) {
		super();
		this.algorithm = algorithm;
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
		Algorithm_Like_Id other = (Algorithm_Like_Id) obj;
		if (algorithm == null) {
			if (other.algorithm != null)
				return false;
		} else if (!algorithm.equals(other.algorithm))
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((algorithm == null) ? 0 : algorithm.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		return result;
	}
	
	
	
	
}
