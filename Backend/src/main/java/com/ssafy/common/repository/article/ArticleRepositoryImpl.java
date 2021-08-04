package com.ssafy.common.repository.article;
import static com.ssafy.common.domain.article.QArticle.article;
import static com.ssafy.common.domain.article.QArticle_Comment.article_Comment;
import static com.ssafy.common.domain.article.QArticle_Like.article_Like;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.QArticle;
import com.ssafy.common.domain.article.QArticle_Algorithm;
import com.ssafy.common.domain.article.QArticle_Comment;
import com.ssafy.common.domain.article.QArticle_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.MemberDTO;
import com.ssafy.common.dto.ProblemSiteDTO;

import lombok.RequiredArgsConstructor;




@Repository
@RequiredArgsConstructor
//public class ArticleRepositorySupport extends QuerydslRepositorySupport{
public class ArticleRepositoryImpl implements ArticleRepositoryCustom{
	private final JPAQueryFactory queryFactory;

  	@Override
  	public Optional<List<ArticleDTO>> sltMulti(Long nowLoginMemberNo,Pageable page){
		QArticle qa=QArticle.article;
		QArticle_Like qal= QArticle_Like.article_Like;
		QArticle_Comment qac=QArticle_Comment.article_Comment;
		
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
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.fetch();
	
		
		return Optional.of(result) ;
	}
  	
	@Override
  	public Optional<List<ArticleDTO>> sltMultiQnA(Long nowLoginMemberNo,Pageable page, Article_Class articleClass){
		QArticle qa=QArticle.article;
		QArticle_Like qal= QArticle_Like.article_Like;
		QArticle_Comment qac=QArticle_Comment.article_Comment;
		
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
			.where(qa.articleClass.eq(articleClass))
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.fetch();
	
		System.out.println(articleClass);
		System.out.println(result.size());
		return Optional.of(result) ;
	}
  	
    //게시글 단건조회
  	@Override
    public ArticleDTO sltOne(Long pk,Long nowLoginMemberNo){
    	
    	QArticle qa=QArticle.article;
		QArticle_Like qal= QArticle_Like.article_Like;
		QArticle_Comment qac=QArticle_Comment.article_Comment;
		
		ArticleDTO result = queryFactory.select(Projections.constructor(ArticleDTO.class
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
			.where(qa.articleNo.eq(pk))
			.fetchOne();
    	
    	return result;
    }
  	//return article이 필요할때
  	@Override
  	public Article sltOneArticle(Long pk) {
		Article sltOne = queryFactory.selectFrom(article)
		.where(article.articleNo.eq(pk))
		.fetchOne();
		return sltOne;
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
    //게시글 Insert 추가적인 gradle import를 요구해서 패스
	@Override
    public Article insertArticle(Member member, Article myinsert){

		return null;
    }
	
	@Override
    public long articleDelete(Long articlePk) {
    	long deletedArticle = queryFactory.delete(article).
    			where(article.articleNo.eq(articlePk)).
    			execute();
    	return deletedArticle;
    			
    }
	@Override
	public long updateArticle(long articlePk, Article updateArticle) {
		long updatedArticle = queryFactory.update(article).
				where(article.articleNo.eq(articlePk))
				.set(article.articleContent, updateArticle.getArticleContent())
				.set(article.articleTitle, updateArticle.getArticleTitle()).
				execute();
						
		return updatedArticle;
	}
	
	@Override
	public long likeArticle(Article article){
		long likeCounting = queryFactory.selectFrom(article_Like).
				where(article_Like.articleNo.eq(article)).
				fetchCount();
		return likeCounting;
	}
	
	//작성자를 통한 게시글 갯수 조회
	@Override
	public long countByMember(Long memberNo) {
		long articleCount = queryFactory.selectFrom(article).
				where(article.member.no.eq(memberNo)).
				fetchCount();
		return articleCount;
	}
	
//	@Override
//	pubic Argorithm sltOneAlgo()
	
	
	  
	// memberNo를 통해 해당 member를 작성자로 가지는 모든 게시글, 좋아요 수 조회, memberNo유저의 좋아요 여부, 댓글 갯수
	@Override
	public Optional<List<ArticleDTO>> getListByMemberNO(Long memberNo,Long nowLoginMemberNo,Pageable page){
		QArticle qa=QArticle.article;
		QArticle_Like qal= QArticle_Like.article_Like;
		
		List<ArticleDTO> result = queryFactory.select(Projections.constructor(ArticleDTO.class
							, qa.articleNo
							,Projections.constructor(MemberDTO.class, qa.member.name,qa.member.no)
							,qa.articleTitle,qa.articleContent,qa.articleDate
							,Projections.constructor(ProblemSiteDTO.class, qa.problemSite)
							,qa.useLanguage.useLanguage, qa.articleClass
							,qa.articleLike.size()
							,ExpressionUtils.as(
			                        JPAExpressions.select(qal.count())
			                        .from(qal)
			                        .where(qal.articleNo.articleNo.eq(qa.articleNo).and(qal.member.no.eq(nowLoginMemberNo))),
			                "likeState")
							,qa.articleComent.size()							
							))
			.from(qa)
			.where(qa.member.no.eq(memberNo))
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.fetch();
	
		
		return Optional.of(result) ;
	}
	
	//문제 검색 
	//sort: 좋아요순, 최신순 같은 정렬기준
	@Override
	public Optional<List<ArticleDTO> > getProblemSearch(Long nowLoginMemberNo,Problem_Site problem, String language,
			List<String> and, List<String> not, Pageable page,String sort){
		QArticle qa=QArticle.article;
		QArticle_Like qal= QArticle_Like.article_Like;
		QArticle_Algorithm qaa=QArticle_Algorithm.article_Algorithm;
		
		//좋아요 갯수, 좋아요상태, 댓글 갯수 
		List<ArticleDTO> result = queryFactory.select(Projections.constructor(ArticleDTO.class
				, qa.articleNo
				,Projections.constructor(MemberDTO.class, qa.member.name,qa.member.no)
				,qa.articleTitle,qa.articleContent,qa.articleDate
				,Projections.constructor(ProblemSiteDTO.class, qa.problemSite)
				,qa.useLanguage.useLanguage, qa.articleClass
				,qa.articleLike.size()
				,ExpressionUtils.as(
                        JPAExpressions.select(qal.count())
                        .from(qal)
                        .where(qal.articleNo.articleNo.eq(qa.articleNo).and(qal.member.no.eq(nowLoginMemberNo))),
                "likeState")
				,qa.articleComent.size()								
				))
				.from(qa)
				.where(qa.problemSite.eq(problem)
						,language==null?null:qa.useLanguage.useLanguage.eq(language)
						,algorithmCondition(and,not,qaa,qa)
						)				
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.orderBy(sort.equals("like")?qa.articleLike.size().asc():qa.articleDate.asc())
				.fetch();
		
		
		return Optional.of(result);
	}
	
	private BooleanBuilder algorithmCondition(List<String> and,List<String> not,QArticle_Algorithm qaa, QArticle qa) {
		 BooleanBuilder builder = new BooleanBuilder();
		 
		 for(String algo: and) {
			 builder.or(qa.articleAlgorithm.contains(JPAExpressions.select(qaa)
			 			.from(qaa)
			 			.where(qaa.algorithmName.algorithmName.eq(algo))
			 			)
					 );
		 }
		 for(String algo: not) {
			 builder.andNot(qa.articleNo.eq(JPAExpressions.select(qaa.articleNo.articleNo)
				 		.from(qaa)
				 		.where(qaa.algorithmName.algorithmName.eq(algo),qaa.articleNo.articleNo.eq(qa.articleNo))
				 		)
					 );
		 }
		 
		return builder;
	}
	

}

