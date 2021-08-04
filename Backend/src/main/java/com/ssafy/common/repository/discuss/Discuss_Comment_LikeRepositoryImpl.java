package com.ssafy.common.repository.discuss;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


import com.ssafy.common.domain.discuss.QDiscuss_Comment_Like;
import com.ssafy.common.domain.discuss.Discuss_Comment_Like;

@Repository
@RequiredArgsConstructor
public class Discuss_Comment_LikeRepositoryImpl implements Discuss_Comment_LikeRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Discuss_Comment_Like ifMemberExist(Long articlePK,Long memberNo) {
		return queryFactory.selectFrom(QDiscuss_Comment_Like.discuss_Comment_Like).
				where(QDiscuss_Comment_Like.discuss_Comment_Like.member.no.eq(memberNo).
						and(QDiscuss_Comment_Like.discuss_Comment_Like.discussCommentNo.discussCommentNo.eq(articlePK))).fetchOne();
	}
	@Override
	public long deleteLike(Long memberNo,Long articlePK) {
		return queryFactory.delete(QDiscuss_Comment_Like.discuss_Comment_Like).
				where(QDiscuss_Comment_Like.discuss_Comment_Like.member.no.eq(memberNo)).
				where(QDiscuss_Comment_Like.discuss_Comment_Like.discussCommentNo.discussCommentNo.eq(articlePK)).
				execute();
	}
	

}
