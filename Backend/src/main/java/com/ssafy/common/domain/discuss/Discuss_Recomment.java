package com.ssafy.common.domain.discuss;

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
public class Discuss_Recomment {
  
  @Id
  @GeneratedValue
  @Column(name = "DISCUSS_RECOMMENT_NO")
  private long discussRecommentNo;

  @ManyToOne
  @JoinColumn(name="DISCUSS_COMMENT_NO")
  private Discuss_Comment discussCommentNo;

  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member;

  @Column(length = 500,name="DISCUSS_RECOMMENT_CONTENT")
  private String discussRecommentContent;

  @Column(name="DISCUSS_RECOMMENT_DATE")
  private Timestamp discussRecommentDate;
}
