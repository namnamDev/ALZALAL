package com.ssafy.common.domain;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.ssafy.common.domain.member.Member;

// import com.ssafy.common.domain.Member;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification {
	@Id
	@GeneratedValue
	@Column(name = "NOTIFICATION_NO")
	// 알림 번호
	private long notificationNo;

	@ManyToOne
	@JoinColumn(name = "NOTIFICATION_SENDER")
	// 보내는사람
	private Member notificationSender;

	@ManyToOne
	@JoinColumn(name = "NOTIFICATION_RECIEVER")
	// 받는사람
	private Member notificationReciever;

	@Column(name = "NOTIFICATION_READ_STATUS")
	// 알림 상태 (확인여부) false: 안읽음 / true: 읽음
	private boolean notificationReadStatus;

	@Column(name = "NOTIFICATION_TARGET_NO")
	private Long notificationTargetNO;

	@Column(length = 3, name = "NOTIFICATION_TASK")
	@Enumerated(EnumType.STRING)
	private Notification_Task notificationTask;

	@Column(length = 3, name = "NOTIFICATION_SUBTASK")
	@Enumerated(EnumType.STRING)
	private Notification_SubTask notificationSubTask;

	@Column(name = "NOTIFICATION_DATE", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime notificationDate;
}
