package com.ssafy.common.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;

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
	
	public Article_Algorithm sltOne(Algorithm algo,Article article){
		
		return queryFactory.selectFrom(article_Algorithm).
				where(article_Algorithm.algorithmName.eq(algo)).
				where(article_Algorithm.articleNo.eq(article)).
				fetchFirst();
	}
	
}
