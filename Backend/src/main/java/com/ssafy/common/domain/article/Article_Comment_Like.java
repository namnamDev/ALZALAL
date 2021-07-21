package com.ssafy.common.domain.article;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Article_Comment_LikePK.class)
public class Article_Comment_Like {
  @Id
  @ManyToOne
  @JoinColumn(name = "ARTICLE_COMMENT_NO")
  private Article_Comment comment;

  @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_NO")
  private Member member;
  
}
