package com.ssafy.common.domain.discuss;

import java.sql.Timestamp;

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
public class Discuss_Comment {
  @Id
  @GeneratedValue
  @Column(name="DISCUSS_COMMENT_NO")
  private long discussCommentNo;

  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member;

  @ManyToOne
  @JoinColumn(name="DISCUSS_NO")
  private Discuss discussNo;

  @Column(name="DISCUSS_COMMENT_CONTENT")
  private String discussCommentContent;

  @Column(name="DISCUSS_COMMENT_DATE")
  private Timestamp discussCommentDate;
}
