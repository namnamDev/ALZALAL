package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.Article_Comment_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.Article_CommentDTO;

public interface Article_Comment_LikeRepositoryCustom {

	Article_Comment_Like ifMemberExist(Long articlePK, Long memberNo);

	long deleteLike(Long memberNo, Long articlePK);


}
