package com.ssafy.common.domain;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Article_Algorithm {

  @ManyToOne
  @JoinColumn(name = "Algorithm_name")
  private Algorithm Algorithm_name;

  @OneToMany
  @JoinColumn(name = "Article_No")
  private Article[] articles;
  // int a = 1
  // int b = 2
  // private list<int> aa;
}
