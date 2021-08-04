package com.ssafy.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.helpme.Helpme_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.dto.Helpme_CommentDTO;

public interface Helpme_CommentRepositoryCustom {


	Optional<List<Helpme_CommentDTO>> artiComments(Long articleNo, Long memberNo, Pageable page);


	Helpme_Comment sltOneArtiCom(Long commentPK);


	long artiComDelete(Long commentPK);


	long artiComUpdate(Long commentPK, String content);


	Helpme_CommentDTO articleComment(Helpme_Comment ArtiCo, Member member);

}
