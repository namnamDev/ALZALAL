package com.ssafy.common.repository.article;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.article.Article_Algorithm;

public interface Article_AlgorithmRepository extends JpaRepository<Article_Algorithm, Long>,Article_AlgorithmRepositoryCustom{

}
