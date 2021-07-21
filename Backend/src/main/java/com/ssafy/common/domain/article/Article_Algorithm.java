package com.ssafy.common.domain.article;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.Algorithm;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Article_AlgorithmPK.class)
public class Article_Algorithm {
  @Id
  @ManyToOne
  @JoinColumn(name = "algorithm_name")
  private Algorithm algorithm_name;
  
  @Id
  @ManyToOne
  @JoinColumn(name = "article_no")
  private Article article_no;
}
