package com.ssafy.common.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Algorithm {
  @Id
  @Column(length = 30)
  private String Algorithm_name;
}
