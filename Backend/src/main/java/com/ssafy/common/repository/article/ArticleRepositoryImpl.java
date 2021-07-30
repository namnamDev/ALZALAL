package com.ssafy.common.repository.article;
import java.util.List;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import static com.ssafy.common.domain.article.QArticle.*;
import static com.ssafy.common.domain.article.QArticle_Like.*;
import static com.ssafy.common.domain.article.QArticle_Comment.*;




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
}

