package com.ssafy.common.repository.discuss;

import com.ssafy.common.domain.article.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussRepository extends JpaRepository<Article, Long>,DiscussRepositoryCustom{
  
}
