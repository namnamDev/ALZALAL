package com.ssafy.common.domain.article;

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
public class Article_Recomment {

  @Id
  @GeneratedValue
  @Column(name = "ARTICLE_RECOMMENT_NO")
  private long articleRecommentNo;

  @ManyToOne
  @JoinColumn(name="ARTICLE_COMMENT_NO")
  private Article_Comment articleCommentNo;
  
  @Column(length = 3000,name="ARTICLE_CONTENT")
  private String articleContent;

  @Column(name = "ARTICLE_RECOMMENT_DATE")
  private Timestamp articleRecommentDate;

  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member;
}
