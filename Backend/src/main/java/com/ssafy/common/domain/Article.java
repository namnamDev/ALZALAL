package com.ssafy.common.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Article {
  @Id
  @GeneratedValue
  private long Article_No;

  @Column(length = 500)
  private String Article_Content;

  @Column
  private java.sql.Timestamp Article_Date;

  // @ManyToOne
  // @JoinColumn(name="Problem_Site")
  // private Problem_Site Problem_Sites;

  // @ManyToOne
  // @JoinColumn(name="Problem_Site")
  // private Problem_Site Problem_No;

  @ManyToOne
  @JoinColumns({
                @JoinColumn(name="Problem_No",referencedColumnName = "Problem_No"),
                @JoinColumn(name="Problem_Site_Name",referencedColumnName = "Problem_Site_Name")
  })
  private Problem_Site problem_site;

  @Column(length = 4)
	@Enumerated(EnumType.STRING)
  private Article_Class article_class;
}
