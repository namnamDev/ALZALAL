package com.ssafy.common.domain.article;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.ssafy.common.domain.member.Member;

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

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="ARTICLE_NO")
  private Article articleNo;

  @Column(length = 3000,name = "ARTICLE_COMMENT_CONTENT")
  private String commentContent;

  @Column(name="ARTICLE_COMMENT_DATE",updatable = false,
		  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime articleCommentDate;
}
