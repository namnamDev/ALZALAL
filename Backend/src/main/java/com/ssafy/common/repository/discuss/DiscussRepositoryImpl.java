package com.ssafy.common.repository.discuss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.discuss.Discuss;
import com.ssafy.common.domain.discuss.Discuss_Host;
import com.ssafy.common.domain.discuss.QDiscuss;
import com.ssafy.common.domain.discuss.QDiscuss_Comment;
import com.ssafy.common.dto.DiscussDTO;
import com.ssafy.common.dto.Discuss_HostDTO;

import lombok.RequiredArgsConstructor;




@Repository
@RequiredArgsConstructor
//public class ArticleRepositorySupport extends QuerydslRepositorySupport{
public class DiscussRepositoryImpl implements DiscussRepositoryCustom{
	private final JPAQueryFactory queryFactory;

	@Override
  	public Optional<List<DiscussDTO>> sltMulti(Pageable page){
  		QDiscuss qd=QDiscuss.discuss;
		QDiscuss_Comment qdc=QDiscuss_Comment.discuss_Comment;
		
		List<DiscussDTO> result = queryFactory.select(Projections.constructor(DiscussDTO.class
							, qd.discussNo
							,Projections.constructor(Discuss_HostDTO.class, qd.discussCompHostNo.discussCompHostNo,qd.discussCompHostNo.discussCompHostName)
							,qd.discussCompName,qd.discussCompProblem,qd.discussDate
							,ExpressionUtils.as(
			                        JPAExpressions.select(qdc.count())
			                        .from(qdc)
			                        .where(qdc.discussNo.discussNo.eq(qd.discussNo)),
			                "commentCount")									
							))
			.from(qd)
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.fetch();
	
		return Optional.of(result) ;
	}
    //게시글 단건조회
  	@Override
    public DiscussDTO sltOne(Long pk,Long nowLoginMemberNo){
    	
  		QDiscuss qd=QDiscuss.discuss;
		QDiscuss_Comment qdc=QDiscuss_Comment.discuss_Comment;
		
		DiscussDTO result = queryFactory.select(Projections.constructor(DiscussDTO.class
							,qd.discussNo
							,qd.discussCompName,qd.discussCompProblem,qd.discussDate
							,ExpressionUtils.as(
			                        JPAExpressions.select(qdc.count())
			                        .from(qdc)
			                        .where(qdc.discussNo.discussNo.eq(qd.discussNo)),
			                "commentCount")									
							))
			.from(qd)
			.where(qd.discussNo.eq(pk))
			.fetchOne();
    	
    	return result;
    }
  	//return article이 필요할때
  	@Override
  	public Discuss sltOneArticle(Long pk) {
  		QDiscuss qd=QDiscuss.discuss;
		Discuss sltOne = queryFactory.selectFrom(qd)
		.where(qd.discussNo.eq(pk))
		.fetchOne();
		return sltOne;
  	}
  	
  	@Override
  	public Optional<List<DiscussDTO>> sltMultiByDisHost(Long HostPK,Pageable page){
  		QDiscuss qd=QDiscuss.discuss;
		QDiscuss_Comment qdc=QDiscuss_Comment.discuss_Comment;
		
		List<DiscussDTO> result = queryFactory.select(Projections.constructor(DiscussDTO.class
							, qd.discussNo
							,Projections.constructor(Discuss_HostDTO.class, qd.discussCompHostNo.discussCompHostNo,qd.discussCompHostNo.discussCompHostName)
							,qd.discussCompName,qd.discussCompProblem,qd.discussDate
							,ExpressionUtils.as(
			                        JPAExpressions.select(qdc.count())
			                        .from(qdc)
			                        .where(qdc.discussNo.discussNo.eq(qd.discussNo)),
			                "commentCount")									
							))
			.from(qd)
			.where(qd.discussCompHostNo.discussCompHostNo.eq(HostPK))
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.fetch();
	
		return Optional.of(result) ;
	}
    //게시글 댓글 조회
    //대댓글 조회 추가예정
//	@Override
//    public List<Article_Comment> articleComments(Article article){
//    	List<Article_Comment> comments = queryFactory.selectFrom(article_Comment).
//    			where(article_Comment.articleNo.eq(article))
//    			.fetch();
//    	return comments;
//    }
	
	@Override
    public long articleDelete(Long articlePk) {
		QDiscuss qd=QDiscuss.discuss;
    	long deletedArticle = queryFactory.delete(qd).
    			where(qd.discussNo.eq(articlePk)).
    			execute();
    	return deletedArticle;
    			
    }
	@Override
	public long updateArticle(long articlePk, String content) {
		QDiscuss qd=QDiscuss.discuss;
		long updatedArticle = queryFactory.update(qd).
				where(qd.discussNo.eq(articlePk)).set
				(qd.discussCompProblem, content).
				execute();
						
		return updatedArticle;
	}
	
//	@Override
//	pubic Argorithm sltOneAlgo()
	
	
	  
	// memberNo를 통해 해당 member를 작성자로 가지는 모든 게시글, 좋아요 수 조회, memberNo유저의 좋아요 여부, 댓글 갯수
//	@Override
//	public Optional<List<ArticleDTO>> getListByMemberNO(Long memberNo,Long nowLoginMemberNo,Pageable page){
//		QArticle qa=QArticle.article;
//		QArticle_Like qal= QArticle_Like.article_Like;
//		QArticle_Comment qac=QArticle_Comment.article_Comment;
//		
//		List<ArticleDTO> result = queryFactory.select(Projections.constructor(ArticleDTO.class
//							, qa.articleNo
//							,Projections.constructor(MemberDTO.class, qa.member.name,qa.member.no)
//							,qa.articleTitle,qa.articleContent,qa.articleDate
//							,Projections.constructor(ProblemSiteDTO.class, qa.problemSite)
//							,qa.useLanguage.useLanguage, qa.articleClass
//							, ExpressionUtils.as(
//			                        JPAExpressions.select(qal.count())
//			                        .from(qal)
//			                        .where(qal.articleNo.articleNo.eq(qa.articleNo)),
//			                "likeCount")
//							,ExpressionUtils.as(
//			                        JPAExpressions.select(qal.count())
//			                        .from(qal)
//			                        .where(qal.articleNo.articleNo.eq(qa.articleNo).and(qal.member.no.eq(nowLoginMemberNo))),
//			                "likeState")
//							,ExpressionUtils.as(
//			                        JPAExpressions.select(qac.count())
//			                        .from(qac)
//			                        .where(qac.articleNo.articleNo.eq(qa.articleNo)),
//			                "commentCount")									
//							))
//			.from(qa)
//			.where(qa.member.no.eq(memberNo))
//			.offset(page.getOffset())
//			.limit(page.getPageSize())
//			.fetch();
//	
//		
//		return Optional.of(result) ;
//	}
//	
	//게시글 다건조회

}

