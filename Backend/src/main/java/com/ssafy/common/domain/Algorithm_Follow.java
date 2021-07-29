package com.ssafy.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.member.Member;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@IdClass(Algorithm_FollowPK.class)
public class Algorithm_Follow {

	@Id
	@ManyToOne
	@JoinColumn(name="FOLLOWING_ALGORITHM_NO")
	private Algorithm followingArgorithmNo;

	//팔로우 하는사람
	@Id
	@ManyToOne
	@JoinColumn(name="FOLLOW_MEMBER_NO")
	private Member memberNo;

}
