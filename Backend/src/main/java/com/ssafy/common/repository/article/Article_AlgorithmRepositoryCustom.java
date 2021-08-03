package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;

public interface Article_AlgorithmRepositoryCustom {

	List<Article_Algorithm> findAll();

	Optional<Article_Algorithm> sltOne(Algorithm algo, Article article);

}
