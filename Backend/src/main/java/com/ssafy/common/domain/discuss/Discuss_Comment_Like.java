package com.ssafy.common.domain.discuss;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
@IdClass(Discuss_Comment_LikePK.class)
public class Discuss_Comment_Like {
  @Id
  @ManyToOne
  @JoinColumn(name="DISCUSS_COMMENT_NO")
  private Discuss_Comment disscussCommentNo;
  @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_NO")
  private Member member;
}
