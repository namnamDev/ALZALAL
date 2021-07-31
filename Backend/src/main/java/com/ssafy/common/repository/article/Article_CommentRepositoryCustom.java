package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.Article_CommentDTO;

public interface Article_CommentRepositoryCustom {


	Article_CommentDTO articleComment(Article_Comment ArtiCo,Member memberDTO);


//	Optional<List<Article_CommentDTO>> artiComments(Article article, Long memberNo, Pageable page);


	Optional<List<Article_CommentDTO>> artiComments(Long articleNo, Long memberNo, Pageable page);

}
