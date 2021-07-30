package com.ssafy.common.repository.article;

import java.util.List;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.Article_CommentDTO;

public interface Article_CommentRepositoryCustom {


	List<Article_CommentDTO> artiComments(Article article, Member memberDTO);

	Article_CommentDTO articleComment(Article_Comment ArtiCo,Member memberDTO);

}
