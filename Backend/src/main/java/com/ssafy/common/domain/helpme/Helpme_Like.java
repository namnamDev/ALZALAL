package com.ssafy.common.domain.helpme;

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
@IdClass(Helpme_LikePK.class)
public class Helpme_Like {
  @Id
  @ManyToOne
  @JoinColumn(name="MEMBER_NO")
  private Member member;

  @Id
  @ManyToOne
  @JoinColumn(name="HELPME_NO")
  private Helpme helpmeNo;
}
