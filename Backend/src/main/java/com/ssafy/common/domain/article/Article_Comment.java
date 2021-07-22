package com.ssafy.common.domain.article;

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
public class Article_Comment {
  @Id
  @GeneratedValue
  @Column(name = "ARTICLE_COMMENT_NO")
  private long articleCommentNo;

  @ManyToOne
  @JoinColumn(name="MEMBER_NO")
  private Member member;

  @ManyToOne
  @JoinColumn(name="ARTICLE_NO")
  private Article articleNo;

  @Column(length = 500,name = "ARTICLE_COMMENT_CONTENT")
  private String articleContent;

  @Column(name="ARTICLE_COMMENT_DATE")
  private Timestamp articleCommentDate;
}
