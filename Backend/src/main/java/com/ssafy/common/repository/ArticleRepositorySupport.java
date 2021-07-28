package com.ssafy.common.repository;
import java.util.List;

import com.querydsl.core.dml.InsertClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.QArticle;
import com.ssafy.common.domain.member.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import static com.ssafy.common.domain.article.QArticle.*;
import static com.ssafy.common.domain.article.QArticle_Comment.*;
// import 




@Repository
public class ArticleRepositorySupport extends QuerydslRepositorySupport{
  private final JPAQueryFactory queryFactory;
  public ArticleRepositorySupport(JPAQueryFactory queryFactory){
      super(Article.class);
      this.queryFactory = queryFactory;
    }

    public List<Article>findAll(){
      List<Article> aa  = queryFactory.selectFrom(article).fetch();
      return aa;
    }
    //게시글 단건조회
    public Article sltOne(Long pk){
    	Article sltOne = queryFactory.selectFrom(article)
    			.where(article.articleNo.eq(pk))
    			.fetchOne();
    	
    	return sltOne;
    }
    //게시글 댓글 조회
    //대댓글 조회 추가예정
    public List<Article_Comment> articleComments(Article article){
    	List<Article_Comment> comments = queryFactory.selectFrom(article_Comment).
    			where(article_Comment.articleNo.eq(article))
    			.fetch();
    	return comments;
    }
    //게시글 Insert 추가적인 gradle import를 요구해서 패스
//    public Article insertArticle(Member member, Article myinsert){
//    	Article article = new Article(myinsert);
//    	QArticle qArticle = QArticle.article;
//    	SQLInsertClause insert = insert(qArticle)
//    	InsertClause insertArticle.setEntityManager(entityManager);(article,myinsert).execute();
//    	new SQLInsertClause()
//    	private SQLqueryFactory sqlQueryFactory;
//    	Article sltOne = queryFactory.
//    }
    public long articleDelete(Long articlePk) {
    	long deletedArticle = queryFactory.delete(article).
    			where(article.articleNo.eq(articlePk)).
    			execute();
    	return deletedArticle;
    			
    }

	public long updateArticle(long articlePk, String content) {
		long updatedArticle = queryFactory.update(article).
				where(article.articleNo.eq(articlePk)).set
				(article.articleContent, content).
				execute();
						
		return updatedArticle;
	}
	
	public long countByMember(Long memberNo) {
		long articleCount = queryFactory.selectFrom(article).
				where(article.member.no.eq(memberNo)).
				fetchCount();
		return articleCount;
	}
}

