package com.ssafy.common.domain.discuss;

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
public class Discuss_Recomment {
  
  @Id
  @GeneratedValue
  @Column(name = "DISCUSS_RECOMMENT_NO")
  private long recomment;

  @ManyToOne
  @JoinColumn(name="DISCUSS_COMMENT_NO")
  private Discuss_Comment comment;

  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member_no;

  @Column(length = 500,name="DISCUSS_RECOMMENT_CONTENT")
  private String content;

  @Column(name="DISCUSS_RECOMMENT_DATE")
  private Timestamp timestamp;
}
