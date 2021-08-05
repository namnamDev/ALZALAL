package com.ssafy.common.domain.article;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.member.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Article_LikePK.class)
public class Article_Like {
	  @Id
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "ARTICLE_NO")
	  private Article articleNo;
	  
	  @Id
	  @ManyToOne
	  @JoinColumn(name = "MEMBER_NO")
	  private Member member;
}
