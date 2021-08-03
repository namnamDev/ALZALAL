package com.ssafy.common.domain.helpme;
import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
public class Helpme_Recomment {
  
  @Id
  @GeneratedValue
  @Column(name = "HELPME_RECOMMENT_NO")
  private long helpmeRecommentNo;

  @ManyToOne
  @JoinColumn(name="HELPME_COMMENT_NO")
  private Helpme_Comment helpmeCommentNo;

  @Column(length = 3000,name="HELPME_RECOMMENT_CONTENT")
  private String  helpmeRecommentContent;

  @Column(name = "HELPME_RECOMMENT_DATE")
  private Timestamp helpmeRecommentDate;

  @ManyToOne
  @JoinColumn(name = "MEMBER_NO")
  private Member member;
}
