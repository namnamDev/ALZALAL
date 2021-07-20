package com.ssafy.common.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

}
