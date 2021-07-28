package com.ssafy.common.domain.member;

import java.io.Serializable;

import lombok.Data;

@Data
public class Member_FollowPK implements Serializable {
	private Long followNo;
	private Long memberNo;

	public Member_FollowPK(Long followNo, Long memberNo) {
		this.followNo = followNo;
		this.memberNo = memberNo;
	}

	public Member_FollowPK() {}

}
