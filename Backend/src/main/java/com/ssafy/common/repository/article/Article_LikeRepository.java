package com.ssafy.common.repository.article;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.article.Article_LikePK;


public interface Article_LikeRepository extends JpaRepository<Article_Like, Article_LikePK>,Article_LikeRepositoryCustom{

	
}
