package com.ssafy.common.domain.member;

import java.io.Serializable;

import lombok.Data;

@Data
public class Member_FollowPK implements Serializable {
	private Long memberNo;
	private Long followNo;

	public Member_FollowPK(Long memberNo, Long followNo) {
		super();
		this.memberNo = memberNo;
		this.followNo = followNo;
	}

	public Member_FollowPK() {}
}
