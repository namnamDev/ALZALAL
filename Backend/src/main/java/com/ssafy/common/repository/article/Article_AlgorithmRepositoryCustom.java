package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQuery;
import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_AlgorithmDTO;

public interface Article_AlgorithmRepositoryCustom {

	List<Article_Algorithm> findAll();

	Optional<Article_Algorithm> sltOne(Algorithm algo, Article article);

	List<Article_Algorithm> sltMultiByArticle(Article article);

	Long deleteArtiAlgo(Algorithm algo);

	List<Article_AlgorithmDTO> sltMultiByArticleDTO(ArticleDTO article);


}
