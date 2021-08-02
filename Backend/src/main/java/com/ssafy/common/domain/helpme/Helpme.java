package com.ssafy.common.domain.helpme;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Helpme {
  @Id
  @GeneratedValue
  @Column(name="HELPME_NO")
  private long helpmeNo;

  @ManyToOne
  @JoinColumns({
                @JoinColumn(name="PROBLEM__NO_HELPME"),
                @JoinColumn(name="PROBLEM_SITE_NAME_HELPME")
  })
  private Problem_Site problemSite;
  
  @ManyToOne
  @JoinColumn(name = "HELPME_SENDER_NO")
  private Member helpmeSenderNo;

  @ManyToOne
  @JoinColumn(name = "HELPME_RECEPTOR_NO")
  private Member helpmeReceptorNo;

  @Column(length = 500,name="HELPME_CONTENT")
  private String helpmeContent;

  @Column(length = 4, name = "HELPME_STATUS")
  @Enumerated(EnumType.STRING)
  private Helpme_Class helpmeStatus;

  @Column(name = "HELPME_DATE",updatable = false,
		  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime helpmeDate;
}
