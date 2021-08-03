package com.ssafy.common.domain.article;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;

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
  private long articleNo;
  
  
  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member; // referenceName은 디폴트로 할당
  // 헌국뇌피셜 할당 자체를 Memeber로 뒀으니 자동으로 pk를 찾아서 정해주는게 아닐까??
  /// name은 어떤이름으로할것인지 정하는것
  @Column(name="ARTICLE_TITLE",length = 500)
  private String articleTitle;
  
  @Column(name="ARTICLE_CONTENT",length = 3000)
  private String articleContent;

  @Column(name="ARTICLE_DATE",updatable = false,
		  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime articleDate;

  @ManyToOne
  @JoinColumns({
                @JoinColumn(name="Problem_No"),
                @JoinColumn(name="Problem_Site_Name")
  })
  private Problem_Site problemSite;

  @ManyToOne
  @JoinColumn(name="USE_LANGUAGE")
  private Use_Language useLanguage;

  @Column(length = 4,name="ARTICLE_CLASS")
	@Enumerated(EnumType.STRING)
  private Article_Class articleClass;
}
