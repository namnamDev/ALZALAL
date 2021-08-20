package com.ssafy.common.repository.article;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.Article_Comment_Like;

public interface Article_Comment_LikeRepository extends JpaRepository<Article_Comment_Like,Long>,Article_Comment_LikeRepositoryCustom{


}
