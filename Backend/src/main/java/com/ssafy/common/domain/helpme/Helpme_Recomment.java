package com.ssafy.common.domain.helpme;
import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.Member;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
public class Helpme_Recomment {
  
  @Id
  @GeneratedValue
  @Column(name = "HELPME_RECOMMENT_NO")
  private long recomment_no;

  @ManyToOne
  @JoinColumn(name="HELPME_COMMENT_NO")
  private Helpme_Comment comment;

  @Column(length = 500,name="HELPME_RECOMMENT_CONTENT")
  private String content;

  @Column(name = "HELPME_RECOMMENT_DATE")
  private Timestamp timestamp;

  @ManyToOne
  @JoinColumn(name = "MEMBER_NO")
  private Member member_no;
}
