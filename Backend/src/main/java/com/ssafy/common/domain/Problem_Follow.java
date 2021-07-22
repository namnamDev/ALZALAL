package com.ssafy.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
@IdClass(Problem_FollowPK.class)
public class Problem_Follow {
  @Id
	@ManyToOne
	@JoinColumn(name="FOLLOW_MEMBER_NO")
	private Member member_no;

  @Id
	@ManyToOne
  @JoinColumns({
                @JoinColumn(name="FOLLOWING_PROBLEM_NO"),
                @JoinColumn(name="FOLLOWING_PROBLEM_SITE_NAME")
  })
	private Problem_Site problem_site;
}
