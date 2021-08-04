package com.ssafy.common.repository;

import org.springframework.stereotype.Repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.helpme.*;
import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.helpme.Helpme_Like;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Helpme_LikeRepositoryImpl implements Helpme_LikeRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Helpme_Like ifMemberExist(Member member) {
		return queryFactory.selectFrom(QHelpme_Like.helpme_Like).
				where(QHelpme_Like.helpme_Like.member.eq(member)).fetchOne();
	}
	@Override
	public long deleteLike(Member member,Helpme article) {
		return queryFactory.delete(QHelpme_Like.helpme_Like).
				where(QHelpme_Like.helpme_Like.member.eq(member)).
				where(QHelpme_Like.helpme_Like.helpmeNo.eq(article)).
				execute();
	}
	
}
