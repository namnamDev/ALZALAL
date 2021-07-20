package com.ssafy.common.domain;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Article_AlgorithmPK.class)
public class Article_Algorithm {
  // @Id
  // @GeneratedValue
  // private long Article_Algorithm_No;
  @Id
  @ManyToOne
  @JoinColumn(name = "Algorithm_name")
  private Algorithm Algorithm_name;
  
  @Id
  @ManyToOne
  @JoinColumn(name = "Article_No")
  private Article Article_No;
  // private ArrayList<Article> articles;
  // int a = 1
  // int b = 2
  // private list<int> aa;
}
