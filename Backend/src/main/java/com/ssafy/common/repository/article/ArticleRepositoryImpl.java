package com.ssafy.common.repository.article;
import static com.ssafy.common.domain.article.QArticle.article;
import static com.ssafy.common.domain.article.QArticle_Comment.article_Comment;
import static com.ssafy.common.domain.article.QArticle_Like.article_Like;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.QArticle;
import com.ssafy.common.domain.article.QArticle_Comment;
import com.ssafy.common.domain.article.QArticle_Like;
import com.ssafy.common.domain.member.Member;
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
    public List<Article>findAll(){
      List<Article> aa  = queryFactory.selectFrom(article).fetch();
      return aa;
    }
    //게시글 단건조회
  	@Override
    public Article sltOne(Long pk){
    	Article sltOne = queryFactory.selectFrom(article)
    			.where(article.articleNo.eq(pk))
    			.fetchOne();
    	
    	return sltOne;
    }
    //게시글 댓글 조회
    //대댓글 조회 추가예정
	@Override
    public List<Article_Comment> articleComments(Article article){
    	List<Article_Comment> comments = queryFactory.selectFrom(article_Comment).
    			where(article_Comment.articleNo.eq(article))
    			.fetch();
    	return comments;
    }
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
	public long updateArticle(long articlePk, String content) {
		long updatedArticle = queryFactory.update(article).
				where(article.articleNo.eq(articlePk)).set
				(article.articleContent, content).
				execute();
						
		return updatedArticle;
	}
	
	@Override
	public long likeArticle(Article article){
//		long test =article.getArticleNo();//pk받아옴
		long likeCounting = queryFactory.selectFrom(article_Like).
				where(article_Like.articleNo.eq(article)).
//				where(article_Like.articleNo.articleNo.eq(test)).
				//article_like의pk인 article객체의 pk인 articleNo와 test와 비교
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
			.where(qa.member.no.eq(memberNo))
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.fetch();
	
		
		return Optional.of(result) ;
	}
	
	//게시글 다건조회

}

