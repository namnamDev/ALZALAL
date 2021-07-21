package com.ssafy.common.domain.article;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.Member;
import com.ssafy.common.domain.Problem_Site;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Article {
  @Id
  @GeneratedValue
  @Column(name="article_no")
  private long article_no;
  
  
  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member_no; // referenceName은 디폴트로 할당
  // 헌국뇌피셜 할당 자체를 Memeber로 뒀으니 자동으로 pk를 찾아서 정해주는게 아닐까??
  /// name은 어떤이름으로할것인지 정하는것

  @Column(length = 500)
  private String Article_Content;

  @Column
  private java.sql.Timestamp Article_Date;

  @ManyToOne
  @JoinColumns({
                @JoinColumn(name="Problem_No"),
                @JoinColumn(name="Problem_Site_Name")
  })
  private Problem_Site problem_site;

  @Column(length = 4)
	@Enumerated(EnumType.STRING)
  private Article_Class article_class;
}
