package com.ssafy.common.repository.article;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.article.Article_Comment;

public interface Article_CommentRepository extends JpaRepository<Article_Comment,Long>,Article_CommentRepositoryCustom{


}
