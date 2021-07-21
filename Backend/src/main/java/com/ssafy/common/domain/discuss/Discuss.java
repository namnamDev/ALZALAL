package com.ssafy.common.domain.discuss;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Discuss {
  @Id
  @GeneratedValue
  @Column(name="DISCUSS_NO")
  private long discussNo;

  @ManyToOne
  @JoinColumn(name="DISCUSS_COMP_HOST_NO")
  private Discuss_Host host;

  @Column(length = 20,name="DSICUSS_COMP_NAME")
  private String compName;

  @Column(length = 20,name="DISCUSS_COMP_PROBLEM")
  private String compProblem;

  @Column(name="DISCUSS_DATE")
  private Timestamp date;
}
