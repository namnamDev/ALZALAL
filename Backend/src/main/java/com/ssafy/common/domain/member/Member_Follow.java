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
  @Id
	@ManyToOne
	@JoinColumn(name="FOLLOW_MEMBER_NO")
	private Member follow_no;

	@Id
	@ManyToOne
	@JoinColumn(name="FOLLOWING_MEMBER_NO")
	private Member member_no;
}
