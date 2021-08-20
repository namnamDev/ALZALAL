package com.ssafy.common.domain.helpme;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Helpme_Comment {
  @Id
  @GeneratedValue
  @Column(name = "HELPME_RECOMMENT_NO")
  private long helpmeCommentNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "HELPME_NO")
  private Helpme helpmeNo;

  @ManyToOne
  @JoinColumn(name="MEMBER_NO")
  private Member member;


  @Column(length = 3000 ,name="HELPME_COMMENT_CONTENT")
  private String helpmeCommentContent;

  @Column(name="HELPME_COMMENT_DATE",updatable = false,
		  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime helpmeCommentDate;
}
