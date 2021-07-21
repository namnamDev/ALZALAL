package com.ssafy.common.domain.discuss;

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
public class Discuss_Comment {
  @Id
  @GeneratedValue
  @Column(name="DISCUSS_COMMENT_NO")
  private long commentNo;

  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member_no;

  @ManyToOne
  @JoinColumn(name="DISCUSS_NO")
  private Discuss discussNo;

  @Column(name="DISCUSS_COMMENT_CONTENT")
  private String content;

  @Column(name="DISCUSS_COMMENT_DATE")
  private Timestamp date;
}
