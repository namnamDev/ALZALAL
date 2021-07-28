package com.ssafy.common.repository;

import com.ssafy.common.domain.article.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>{
  
}
