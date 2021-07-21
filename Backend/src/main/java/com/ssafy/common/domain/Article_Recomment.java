package com.ssafy.common.domain;

import java.security.Timestamp;

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
public class Article_Recomment {

  @Id
  @GeneratedValue
  @Column(name = "ARTICLE_RECOMMENT_NO")
  private long article_no;

  @ManyToOne
  @JoinColumn(name="ARTICLE_COMMENT_NO")
  private Article_Comment recomment;
  
  @Column(length = 500)
  private String content;

  @Column(name = "ARTICLE_RECOMMENT_DATE")
  private Timestamp timestamp;
}
