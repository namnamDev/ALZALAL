package com.ssafy.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
@IdClass(Problem_SitePK.class)
public class Problem_Site {
  @Id
  @Column(length = 30)
  private String Problem_Site_Name;

  @Id
  @Column
  private int Problem_No;

  @Column(length = 500)
  private String Problem_Site_Link;
}
