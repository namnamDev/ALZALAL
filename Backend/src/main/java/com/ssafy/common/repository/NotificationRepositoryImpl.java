package com.ssafy.common.repository;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ssafy.common.dto.NotificationDTO;

import lombok.RequiredArgsConstructor;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.QNotification;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom{
	
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<List<NotificationDTO>> notiList(Long memberNo,Pageable page){
		QNotification qn = QNotification.notification;
		List<NotificationDTO>res = queryFactory.select(Projections.constructor(NotificationDTO.class
				,qn.notificationNo
				,qn.notificationSender.no
				,qn.notificationSender.name
				,qn.notificationReadStatus
				,qn.notificationTargetNO
				,qn.notificationTask
				,qn.notificationSubTask
				,qn.notificationDate)
				).from(qn)
				.where(qn.notificationReciever.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.orderBy(qn.notificationDate.desc())
				.fetch();
		return Optional.of(res) ;
	}
}
