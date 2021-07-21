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
@IdClass(Article_Recomment_LikePK.class)
public class Article_Recomment_Like {
  @Id
  @ManyToOne
  @JoinColumn(name="ARTICLE_RECOMMENT_NO")
  private Article_Recomment recomment;

  @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_NO")
  private Member member;
}
