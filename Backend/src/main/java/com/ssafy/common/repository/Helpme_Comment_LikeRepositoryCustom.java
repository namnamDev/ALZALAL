package com.ssafy.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.Article_Comment_Like;
import com.ssafy.common.domain.discuss.Discuss_Comment_Like;
import com.ssafy.common.domain.helpme.Helpme_Comment_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.Article_CommentDTO;

public interface Helpme_Comment_LikeRepositoryCustom {

	Helpme_Comment_Like ifMemberExist(Long articlePK, Long memberNo);

	long deleteLike(Long memberNo, Long articlePK);


}
