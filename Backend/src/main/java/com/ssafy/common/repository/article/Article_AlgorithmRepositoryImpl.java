package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.domain.article.QArticle_Algorithm;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_AlgorithmDTO;

import lombok.RequiredArgsConstructor;
import static com.ssafy.common.domain.article.QArticle_Algorithm.*;
@Repository
@RequiredArgsConstructor
public class Article_AlgorithmRepositoryImpl implements Article_AlgorithmRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public List<Article_Algorithm>findAll(){
		
		return queryFactory.select(article_Algorithm).fetch();
	
	}
	@Override
	public Optional<Article_Algorithm> sltOne(Algorithm algo,Article article){
		
		return Optional.ofNullable(queryFactory.selectFrom(article_Algorithm).
				where(article_Algorithm.algorithmName.eq(algo)).
				where(article_Algorithm.articleNo.eq(article)).
				fetchFirst());
	}
	@Override
	public List<Article_Algorithm> sltMultiByArticle(Article article){
		
		return queryFactory.selectFrom(article_Algorithm).
				where(article_Algorithm.articleNo.eq(article)).fetch();
	}
	
	@Override
	public Long deleteArtiAlgo(Algorithm algo){
		return queryFactory.delete(article_Algorithm).
				where(article_Algorithm.algorithmName.eq(algo)).
				execute();
	}
	@Override
	public List<Article_AlgorithmDTO> sltMultiByArticleDTO(ArticleDTO article){
		QArticle_Algorithm qaa = QArticle_Algorithm.article_Algorithm;
		return queryFactory.select(Projections.constructor(Article_AlgorithmDTO.class
				, qaa.articleNo.articleNo
				, qaa.algorithmName.algorithmName
				)).from(qaa).
				where(article_Algorithm.articleNo.articleNo.eq(article.getArticleNo())).fetch();
	}
}
