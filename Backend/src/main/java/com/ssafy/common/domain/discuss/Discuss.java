package com.ssafy.common.domain.discuss;


import java.sql.Timestamp;
import java.time.LocalDateTime;

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
  private Discuss_Host discussCompHostNo;

  @Column(length = 200,name="DSICUSS_COMP_NAME")
  private String discussCompName;

  @Column(length = 200,name="DISCUSS_COMP_PROBLEM")
  private String discussCompProblem;

  @Column(name="DISCUSS_DATE")
  private LocalDateTime discussDate;
}
