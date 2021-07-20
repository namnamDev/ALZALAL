package com.ssafy.common.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

  @ManyToOne
  @JoinColumn(name="Problem_Site")
  private Problem_Site Problem_Sites;

  @Column(length = 4)
	@Enumerated(EnumType.STRING)
  private Article_Class Article_Class;
}
