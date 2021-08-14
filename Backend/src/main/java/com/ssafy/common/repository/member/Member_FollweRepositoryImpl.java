package com.ssafy.common.repository.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.QAlgorithm_Follow;
import com.ssafy.common.domain.article.QArticle;
import com.ssafy.common.domain.article.QArticle_Algorithm;
import com.ssafy.common.domain.article.QArticle_Comment;
import com.ssafy.common.domain.article.QArticle_Like;
import com.ssafy.common.domain.member.QMember_Follow;
import com.ssafy.common.domain.problem.QProblem_Follow;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.FollowMemberDto;
import com.ssafy.common.dto.MemberDTO;
import com.ssafy.common.dto.ProblemSiteDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member_FollweRepositoryImpl
		implements
			Member_FollowRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	// memberNo를 팔로우 한 사람들(팔로워)
	@Override
	public List<FollowMemberDto> getFollowers(Long memberNo,
			Long nowLoginMemberNo, Pageable page) {
		QMember_Follow mf1 =new QMember_Follow("mf1");
		QMember_Follow mf2 =new QMember_Follow("mf2");


		List<FollowMemberDto> result = queryFactory
				.select(Projections.constructor(FollowMemberDto.class,
						mf1.memberNo.no, mf1.memberNo.name,
						ExpressionUtils.as(JPAExpressions
								.select(mf2.count())
								.from(mf2)
								.where(mf2.memberNo.no.eq(nowLoginMemberNo),
										mf2.followNo.no.eq(mf1.memberNo.no)),
								"followState")))
				.from(mf1)
				.where(mf1.followNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return result;
	}

	// memberNo가 팔로우 한 사람들(사람 팔로잉)
	@Override
	public List<FollowMemberDto> getMemberFollowings(Long memberNo,
			Long nowLoginMemberNo, Pageable page) {
		QMember_Follow mf1 =new QMember_Follow("mf1");
		QMember_Follow mf2 =new QMember_Follow("mf2");

		List<FollowMemberDto> result = queryFactory
				.select(Projections.constructor(FollowMemberDto.class,
						mf1.followNo.no, mf1.followNo.name,
						ExpressionUtils.as(JPAExpressions
								.select(mf2.count())
								.from(mf2)
								.where(mf2.memberNo.no.eq(nowLoginMemberNo),
										mf2.followNo.no.eq(mf1.followNo.no)),
								"followState")))
				.from(mf1)
				.where(mf1.memberNo.no.eq(memberNo))
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetch();

		return result;
	}
	// memberNo가 팔로우 한 사람들이 쓴 글 조회
	@Override
	public List<ArticleDTO>getFollowingArticle(Long nowLoginMemberNo,Pageable page){
		
		QArticle_Like qal= QArticle_Like.article_Like;
		QArticle_Comment qac=QArticle_Comment.article_Comment;
		QMember_Follow qmf = QMember_Follow.member_Follow;
		QArticle qa=QArticle.article;
		QAlgorithm_Follow qaf = QAlgorithm_Follow.algorithm_Follow;
		QProblem_Follow qpf = QProblem_Follow.problem_Follow;
		QArticle_Algorithm qaa = QArticle_Algorithm.article_Algorithm; 

		List<ArticleDTO> result = queryFactory.select(Projections.constructor(ArticleDTO.class
							, qa.articleNo
							,Projections.constructor(MemberDTO.class, qa.member.name,qa.member.no)
							,qa.articleTitle,qa.articleContent,qa.articleDate
							,Projections.constructor(ProblemSiteDTO.class, qa.problemSite)
							,qa.useLanguage.useLanguage, qa.articleClass
							, ExpressionUtils.as(
			                        JPAExpressions.select(qal.count())
			                        .from(qal)
			                        .where(qal.articleNo.articleNo.eq(qa.articleNo)),
			                "likeCount")
							,ExpressionUtils.as(
			                        JPAExpressions.select(qal.count())
			                        .from(qal)
			                        .where(qal.articleNo.articleNo.eq(qa.articleNo).and(qal.member.no.eq(nowLoginMemberNo))),
			                "likeState")
							,ExpressionUtils.as(
			                        JPAExpressions.select(qac.count())
			                        .from(qac)
			                        .where(qac.articleNo.articleNo.eq(qa.articleNo)),
			                "commentCount")									
							))
			.from(qa)
			.where(
					(
							(qa.member.no.eqAny(JPAExpressions.select(qmf.followNo.no)
							.from(qmf)
							.where(qmf.memberNo.no.eq(nowLoginMemberNo))
			                ))
					.or(
							qa.problemSite.eqAny(
							JPAExpressions.select(qpf.followingProblemSiteName)
							.from(qpf).where(qpf.member.no.eq(nowLoginMemberNo))
							)
						)
					
					.or(
							qa.in(JPAExpressions.select(qaa.articleNo)
									.from(qaa).where(qaa.algorithmName
											.in(JPAExpressions.select(qaf.followingArgorithmNo)
											.from(qaf).where(qaf.memberNo.no.eq(nowLoginMemberNo))
											)))
						)
					.or(//자기가 작성한 글도 나오게
							qa.member.no.eq(nowLoginMemberNo)							
						)
					)
				)
			
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.orderBy(qa.articleDate.desc())
			.fetch();


		return result;
		
	}

}
