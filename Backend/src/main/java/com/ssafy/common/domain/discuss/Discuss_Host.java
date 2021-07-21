package com.ssafy.common.domain.discuss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Discuss_Host {
  @Id
  @GeneratedValue
  @Column(name="DISCUSS_COMP_HOST_NO")
  private long no;

  @Column(length=30,name="DISCUSS_COMP_HOST_NAME")
  private String name;
}
