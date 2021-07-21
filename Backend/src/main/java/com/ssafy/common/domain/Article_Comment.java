package com.ssafy.common.domain;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Article_Comment {
  @Id
  @GeneratedValue
  @Column(name = "ARTICLE_COMMENT_NO")
  private long article_comment_no;

  @ManyToOne
  @JoinColumn(name="MEMBER_NO")
  private Member member;

  @ManyToOne
  @JoinColumn(name="ARTICLE_NO")
  private Article article;

  @Column(length = 500,name = "ARTICLE_COMMENT_CONTENT")
  private String content;

  @Column(name="ARTICLE_COMMENT_DATE")
  private Time date;
}
