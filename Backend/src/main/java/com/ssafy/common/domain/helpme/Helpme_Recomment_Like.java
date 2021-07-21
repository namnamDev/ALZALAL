package com.ssafy.common.domain.helpme;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Helpme_Recomment_LikePK.class)
public class Helpme_Recomment_Like {
  @Id
  @ManyToOne
  @JoinColumn(name="HELPME_COMMENT_NO")
  private Helpme_Recomment recomment;
  
  @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_NO")
  private Member member;
}
