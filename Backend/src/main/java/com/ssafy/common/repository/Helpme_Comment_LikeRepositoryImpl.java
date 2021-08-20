package com.ssafy.common.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import com.ssafy.common.domain.helpme.Helpme_Comment_Like;
import com.ssafy.common.domain.helpme.QHelpme_Comment_Like;

@Repository
@RequiredArgsConstructor
public class Helpme_Comment_LikeRepositoryImpl implements Helpme_Comment_LikeRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Helpme_Comment_Like ifMemberExist(Long articlePK,Long memberNo) {
		return queryFactory.selectFrom(QHelpme_Comment_Like.helpme_Comment_Like).
				where(QHelpme_Comment_Like.helpme_Comment_Like.member.no.eq(memberNo).
						and(QHelpme_Comment_Like.helpme_Comment_Like.helpmeCommentNo.helpmeCommentNo.eq(articlePK))).fetchOne();
	}
	@Override
	public long deleteLike(Long memberNo,Long articlePK) {
		return queryFactory.delete(QHelpme_Comment_Like.helpme_Comment_Like).
				where(QHelpme_Comment_Like.helpme_Comment_Like.member.no.eq(memberNo)).
				where(QHelpme_Comment_Like.helpme_Comment_Like.helpmeCommentNo.helpmeCommentNo.eq(articlePK)).
				execute();
	}
	

}
