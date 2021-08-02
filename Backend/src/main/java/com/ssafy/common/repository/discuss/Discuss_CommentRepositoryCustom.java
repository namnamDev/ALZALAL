package com.ssafy.common.repository.discuss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.discuss.Discuss_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.dto.Discuss_CommentDTO;

public interface Discuss_CommentRepositoryCustom {


	Discuss_CommentDTO articleComment(Article_Comment ArtiCo,Member memberDTO);


//	Optional<List<Article_CommentDTO>> artiComments(Article article, Long memberNo, Pageable page);


	Optional<List<Discuss_CommentDTO>> artiComments(Long articleNo, Long memberNo, Pageable page);


	Discuss_Comment sltOneArtiCom(Long commentPK);


	long artiComDelete(Long commentPK);


	long artiComUpdate(Long commentPK, String content);

}
