package com.ssafy.common.domain;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// import com.ssafy.common.domain.Member;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Notification {
  @Id
  @GeneratedValue
  @Column(name="NOTIFICATION_NO")
  private long no;

  @ManyToOne
  @JoinColumn(name = "NOTIFICATION_SENDER")
  private Member sender;

  @ManyToOne
  @JoinColumn(name = "NOTIFICATION_RECIEVER")
  private Member reciever;

  @Column(name="NOTIFICATION_READ_STATUS")
  private boolean status;

  @Column(length =3,name= "NOTIFICATION_TABLE_CLASS")
  @Enumerated(EnumType.STRING)
  private Notification_Class nClass;

  @Column(name = "NOTIFICATION_DATE")
  private Timestamp date;
}
