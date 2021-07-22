package com.ssafy.common.domain.helpme;
import java.sql.Timestamp;

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
public class Helpme_Comment {
  @Id
  @GeneratedValue
  @Column(name = "HELPME_RECOMMENT_NO")
  private long helpmeRecoomentNo;

  @ManyToOne
  @JoinColumn(name = "HELPME_NO")
  private Helpme helpmeNo;

  @ManyToOne
  @JoinColumn(name="MEMBER_NO")
  private Member member;


  @Column(length = 500 ,name="HELPME_COMMENT_CONTENT")
  private String helpmeCommentContent;

  @Column(name="HELPME_COMMENT_DATE")
  private Timestamp helpmeCommentDate;
}
