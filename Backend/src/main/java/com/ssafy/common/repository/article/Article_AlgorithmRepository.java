package com.ssafy.common.repository.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.dto.ArticleDTO;

public interface Article_AlgorithmRepository extends JpaRepository<Article_Algorithm, Long>,Article_AlgorithmRepositoryCustom{

}
