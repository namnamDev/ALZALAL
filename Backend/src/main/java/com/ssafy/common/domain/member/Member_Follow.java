package com.ssafy.common.domain.member;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Member_FollowPK.class)
public class Member_Follow {
	//팔로우 하는사람
	@Id
	@ManyToOne
	@JoinColumn(name="FOLLOW_MEMBER_NO")
	private Member memberNo;

	//팔로우 당하는 사람
	@Id
	@ManyToOne
	@JoinColumn(name="FOLLOWING_MEMBER_NO")
	private Member followNo;
}
